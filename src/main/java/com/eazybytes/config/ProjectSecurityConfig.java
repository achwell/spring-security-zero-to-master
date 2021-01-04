package com.eazybytes.config;

import com.eazybytes.controller.*;
import com.eazybytes.filter.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";

    @Value("${h2.console.path:/h2-console}")
    String h2Console;

    @Value("${jwt.signing.key}")
    private String tokenHeader;

    @Value("${jwt.expire.timeout.ms}")
    private Long jwtExpiresTimeoutMs;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = sessionMangement(http);
        httpSecurity = cors(httpSecurity);
        httpSecurity = csrf(httpSecurity);
        httpSecurity = filters(httpSecurity);
        httpSecurity = matchers(httpSecurity);
        finish(httpSecurity);
    }

    private HttpSecurity sessionMangement(HttpSecurity http) throws Exception {
        return http.sessionManagement().sessionCreationPolicy(STATELESS).and();
    }

    private HttpSecurity cors(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(singletonList("http://localhost:4200"));
                    config.setAllowedMethods(singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(singletonList("*"));
                    config.setExposedHeaders(singletonList(AUTHORIZATION));
                    config.setMaxAge(3600L);
                    return config;
                }
        ).and();
    }

    private HttpSecurity csrf(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable();
    }

    private HttpSecurity filters(HttpSecurity httpSecurity) {
        return httpSecurity
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(tokenHeader), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(tokenHeader, jwtExpiresTimeoutMs), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class);
    }

    private HttpSecurity matchers(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers(AccountController.URL).hasRole(ROLE_USER)
                .antMatchers(BalanceController.URL).hasAnyRole(ROLE_USER, ROLE_ADMIN)
                .antMatchers(LoansController.URL).hasAnyRole(ROLE_USER, ROLE_ADMIN)
                .antMatchers(CardsController.URL).authenticated()
                .antMatchers(LoginController.URL).authenticated()
                .antMatchers(NoticesController.URL).permitAll()
                .antMatchers(ContactController.URL).permitAll()
                .antMatchers(h2Console + "/**").permitAll()
                .and();
    }

    private void finish(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().frameOptions().sameOrigin().and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
