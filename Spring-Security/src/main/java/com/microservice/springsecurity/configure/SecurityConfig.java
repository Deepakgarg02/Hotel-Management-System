package com.microservice.springsecurity.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microservice.springsecurity.filter.JwtFilterRequest;
import com.microservice.springsecurity.service.UserService;

// Important Points 

//Avoid duplicate usernames during sign-up
//Password validation
//Set the expiration time for JWT tokens to 30 minutes
//Utilize JWT tokens to implement role-based authorization
//Email validation

//It extends the WebSecurityConfigurerAdapter class, 
//which provides default security configurations and allows customization.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtFilterRequest jwtFilter;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Configure the AuthenticationManagerBuilder to use the user service for user authentication
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF protection and define the authorization rules
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/subs", "/auth")
                .permitAll() // Allow access to /subs and /auth without authentication
                .anyRequest()
                .authenticated(); // Require authentication for any other request

        // Add the JwtFilterReq before the UsernamePasswordAuthenticationFilter in the filter chain
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use NoOpPasswordEncoder for simplicity, but it's not recommended for production use
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Expose the AuthenticationManager bean
        return super.authenticationManagerBean();
    }
}

