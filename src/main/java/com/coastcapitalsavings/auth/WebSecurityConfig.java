package com.coastcapitalsavings.auth;

import com.coastcapitalsavings.auth.jwt.JwtAuthFilter;
import com.coastcapitalsavings.auth.jwt.JwtAuthenticationEntryPoint;
import com.coastcapitalsavings.auth.jwt.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    DataSource dataSource;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthEndPoint;


    public void configAuthentication(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/authenticate");

        http.authorizeRequests()
                .antMatchers("/authenticate")
                .permitAll()
                .antMatchers("/**/*")
                .denyAll();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/hello").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                    .formLogin().loginPage("/authenticate")
                    .usernameParameter("username").passwordParameter("password")
                .and()
                    .logout().logoutSuccessUrl("/authenticate?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                    .csrf();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)  throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
}
