package com.demo.demoApp.controller;

import com.demo.demoApp.Mapper.AccountMapper;
import com.demo.demoApp.Mapper.UserMapper;
import com.demo.demoApp.model.Account;
import com.demo.demoApp.DTO.AccountGetDTO;
import com.demo.demoApp.model.User;
import com.demo.demoApp.DTO.UserDTO;
import com.demo.demoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/user")
public class UserController {


   private final UserService userService;
   private final UserMapper userMapper;
   private final AccountMapper accountMapper;
    @Autowired
    public UserController(UserService userService, UserMapper userMapper, AccountMapper accountMapper) {

        this.userService = userService;
        this.userMapper = userMapper;
        this.accountMapper = accountMapper;
    }

    //create user
    @PostMapping("/create")
    public UserDTO createUser(Principal principal){
        //Create user with username of current logged in user (InMemoryAuthentication)
        User user = userService.createUser(principal);
        return userMapper.toUserDTO(user);
    }

    //get all user
    @GetMapping("/all")
    public Iterable<UserDTO> getAllUsers(){

        Iterable<User> users = userService.getAllUsers();
        return userMapper.toUserDTOs(users);
    }

    //Get user by username
    @GetMapping("/{userName}")
    public UserDTO getUser(@PathVariable String userName){

        User user = userService.getUser(userName);
        return userMapper.toUserDTO(user);
    }

    //Get user accounts by username
    @GetMapping("/acc/{userName}")
    public Iterable<AccountGetDTO> getAccounts(@PathVariable String userName){
        Iterable<Account> accounts = userService.getAccounts(userName);
        return accountMapper.toAccountGetDTOs(accounts);
    }




}
