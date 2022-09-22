package com.demo.demoApp.controller;

import com.demo.demoApp.Mapper.AccountMapper;
import com.demo.demoApp.model.Account;
import com.demo.demoApp.DTO.AccountGetDTO;
import com.demo.demoApp.DTO.AccountPostDTO;
import com.demo.demoApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@EnableTransactionManagement
@RequestMapping("/acc")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    //create account
    @PostMapping("/create")
    public AccountGetDTO createAccount(Principal principal, @RequestBody AccountPostDTO accountPostDTO){
        //Create bank account
        String useName = principal.getName();

        Account account = accountMapper.toAccount(accountPostDTO);
        return accountMapper.toAccountGetDTO(accountService.createAccount(useName, account));
    }

    //Get all accounts from database
    @GetMapping("/all")
    public Iterable<AccountGetDTO> getAllAccounts(){

        Iterable<Account> accounts = accountService.getAllAccounts();
        Iterable<AccountGetDTO> accountDTOs = accountMapper.toAccountGetDTOs(accounts);
        return accountDTOs;
    }

    //add money to account
    @PutMapping("/add/{accName}/{value}")
    public AccountGetDTO addMoney(Principal principal, @PathVariable String accName, @PathVariable float value){
        AccountGetDTO accountGetDTO = accountMapper.toAccountGetDTO(accountService.addMoney(principal.getName(), accName, value));
        return accountGetDTO;
    }

    //Send money from one account to another
    @PutMapping("/send")
    public Iterable<AccountGetDTO> sendMoneyToAcc(Principal principal, @RequestParam(name = "accName")  String accName, @RequestParam(name = "userNameRec") String userNameRec, @RequestParam(name = "accNameRec") String accNameRec, @RequestParam(name = "value") float value){
        Iterable<Account> accounts = accountService.sendMoneyToAcc(userNameRec, accNameRec, principal.getName(), accName, value);
        return accountMapper.toAccountGetDTOs(accounts);
    }

    //delete account
    @DeleteMapping("/delete")
    public String deleteAcc(Principal principal, @RequestParam(name = "accName") String accName){
        return accountService.deleteAcc(principal.getName(), accName);
    }
}
