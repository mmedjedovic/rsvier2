package com.rsvier.workshop.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        UserDetails u1 = User.withDefaultPasswordEncoder().username("filip").password("kaasboer").roles("MEDEWERKER").build();

        UserDetails u2 = User.withDefaultPasswordEncoder().username("marko").password("melkboer").roles("MEDEWERKER").build();

        auth.inMemoryAuthentication().withUser(u1).withUser(u2);

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests().antMatchers("/klant", "/artikel", "/bestelling").access("hasRole('ROLE_MEDEWERKER')").and().formLogin();

        httpSecurity.csrf().disable();

        httpSecurity.headers().frameOptions().disable();

    }
}
