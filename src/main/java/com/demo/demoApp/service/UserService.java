package com.demo.demoApp.service;

import com.demo.demoApp.Exception.UserExceptionReq;
import com.demo.demoApp.model.Account;
import com.demo.demoApp.model.User;
import com.demo.demoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //Creating user from InMemoryUser
    public User createUser(Principal principal){
        //check if user exist
        String username = principal.getName();
        if(userRepository.findByUserName(username) != null){
            throw new UserExceptionReq("User " + username + " already exist");
        }

        //if user with current username not exist create new
        User user = new User();
        user.setUserName(username);
        userRepository.save(user);

        return user;
    }

    public Iterable<User> getAllUsers(){
        return  userRepository.findAll();
    }

    // Get user by username
    public User getUser(String userName){

        // check if user exist
        if(userRepository.findByUserName(userName) == null){
            throw new UserExceptionReq("User " + userName + "Not Exist");
        }

        User user = userRepository.findByUserName(userName);
        return user;
    }

    //Get user accounts
    public Iterable<Account> getAccounts(String userName){

        // check if user exist
        if(userRepository.findByUserName(userName) == null){
            throw new UserExceptionReq("User " + userName + "Not Exist");
        }

       Set<Account> userAccounts =  userRepository.findByUserName(userName).getAccounts();
        return userAccounts;
    }


}
