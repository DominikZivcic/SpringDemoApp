package com.demo.demoApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfiguration {
/*    @Bean
    protected InMemoryUserDetailsManager configure(){
        List<UserDetails> userDetails = new ArrayList<>();
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        //Authorities
        userAuthorities.add(new SimpleGrantedAuthority("USER"));

        //Creating a new users
        userDetails.add(new User("User1", "{noop}password1", userAuthorities));
        userDetails.add(new User("User2", "{noop}password2", userAuthorities));
        userDetails.add(new User("User3", "{noop}password3", userAuthorities));

        return  new InMemoryUserDetailsManager(userDetails);

    }*/

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.httpBasic()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/**").hasRole("SUPER_ADMIN");
        return http.build();

    }


}
