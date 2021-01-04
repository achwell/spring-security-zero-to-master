package com.eazybytes.config;

import com.eazybytes.controller.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import static java.util.Collections.singletonList;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${h2.console.path:/h2-console}")
    String h2Console;

    /**
     * /myAccount - Secured
     * /myBalance - Secured
     * /myLoans - Secured
     * /myCards - Secured
     * /user - Secured
     * /notices - Not Secured
     * /contact - Not Secured
     */
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
                .and().authorizeRequests()
                .antMatchers(AccountController.URL).authenticated()
                .antMatchers(BalanceController.URL).authenticated()
                .antMatchers(LoansController.URL).authenticated()
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
