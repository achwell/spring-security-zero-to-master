package com.eazybytes.config;

import com.eazybytes.controller.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * /myAccount - Secured
     * /myBalance - Secured
     * /myLoans - Secured
     * /myCards - Secured
     * /notices - Not Secured
     * /contact - Not Secured
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        secureAll(http);
//        perUrlRequirement(http);
//        denyAll(http);
        permitAll(http);

    }

    /*
     * Default configurations which will secure all the requests
     */
    private void secureAll(HttpSecurity http) throws Exception {
        httpBasicFormLogin(
                getExpressionInterceptUrlRegistry(http)
                        .anyRequest().authenticated()
        );
    }

    /*
     * Custom configurations as per url requirement
     */
    private void perUrlRequirement(HttpSecurity http) throws Exception {
        httpBasicFormLogin(
                getExpressionInterceptUrlRegistry(http)
                        .antMatchers(AccountController.URL).authenticated()
                        .antMatchers(BalanceController.URL).authenticated()
                        .antMatchers(LoansController.URL).authenticated()
                        .antMatchers(CardsController.URL).authenticated()
                        .antMatchers(NoticesController.URL).permitAll()
                        .antMatchers(ContactController.URL).permitAll()
        );
    }

    /*
     * Configuration to deny all the requests
     */
    private void denyAll(HttpSecurity http) throws Exception {
        httpBasicFormLogin(
                getExpressionInterceptUrlRegistry(http)
                        .anyRequest().denyAll()
        );
    }

    /*
     * Configuration to permit all the requests
     */
    private void permitAll(HttpSecurity http) throws Exception {
        httpBasicFormLogin(
                getExpressionInterceptUrlRegistry(http)
                        .anyRequest().permitAll()
        );
    }

    private ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry getExpressionInterceptUrlRegistry(HttpSecurity http) throws Exception {
        return http.authorizeRequests();
    }

    private void httpBasicFormLogin(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry) throws Exception {
        expressionInterceptUrlRegistry
                .and().formLogin()
                .and().httpBasic();
    }

}
