package com.demo.demoApp.config;

import com.demo.demoApp.model.User;
import com.demo.demoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceDemo implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password("{noop}"+user.getPassword())
                .disabled(false)
                .authorities("ROLE_"+user.getRole())
                .build();

        return userDetails;
    }
}
