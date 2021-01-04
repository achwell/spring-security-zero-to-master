package com.eazybytes.config;

import com.eazybytes.controller.*;
import com.eazybytes.filter.AuthoritiesLoggingAfterFilter;
import com.eazybytes.filter.AuthoritiesLoggingAtFilter;
import com.eazybytes.filter.RequestValidationBeforeFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import static java.util.Collections.singletonList;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_ROOT = "ROOT";

    @Value("${h2.console.path:/h2-console}")
    String h2Console;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(singletonList("http://localhost:4200"));
            config.setAllowedMethods(singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(singletonList("*"));
            config.setMaxAge(3600L);
            return config;
        })
                .and().csrf().ignoringAntMatchers("/contact").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().csrf().ignoringAntMatchers(h2Console + "/**")
                .and().addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(AccountController.URL).hasRole(ROLE_USER)
                .antMatchers(BalanceController.URL).hasAnyRole(ROLE_USER,ROLE_ADMIN)
                .antMatchers(LoansController.URL).hasRole(ROLE_ROOT)
                .antMatchers(CardsController.URL).authenticated()
                .antMatchers(LoginController.URL).authenticated()
                .antMatchers(NoticesController.URL).permitAll()
                .antMatchers(ContactController.URL).permitAll()
                .antMatchers(h2Console + "/**").permitAll()
                .and().headers().frameOptions().sameOrigin()
                .and().httpBasic();
    }

    /*
     * @Override protected void configure(AuthenticationManagerBuilder auth) throws
     * Exception {
     * auth.inMemoryAuthentication().withUser("admin").password("12345").authorities
     * ("admin").and(). withUser("user").password("12345").authorities("read").and()
     * .passwordEncoder(NoOpPasswordEncoder.getInstance()); }
     */

    /*
     * @Override protected void configure(AuthenticationManagerBuilder auth) throws
     * Exception { InMemoryUserDetailsManager userDetailsService = new
     * InMemoryUserDetailsManager(); UserDetails user =
     * User.withUsername("admin").password("12345").authorities("admin").build();
     * UserDetails user1 =
     * User.withUsername("user").password("12345").authorities("read").build();
     * userDetailsService.createUser(user); userDetailsService.createUser(user1);
     * auth.userDetailsService(userDetailsService); }
     */

    /*
     * @Bean public UserDetailsService userDetailsService(DataSource dataSource) {
     * return new JdbcUserDetailsManager(dataSource); }
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
