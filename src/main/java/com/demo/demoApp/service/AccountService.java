package com.demo.demoApp.service;

import com.demo.demoApp.Exception.AccountExceptionReq;
import com.demo.demoApp.Exception.UserExceptionReq;
import com.demo.demoApp.model.Account;
import com.demo.demoApp.model.AccountType;
import com.demo.demoApp.model.User;
import com.demo.demoApp.repository.AccountRepository;
import com.demo.demoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    final float knEur = 7.5f;
    public Account createAccount(String userName, Account account){

        String accName = account.getAccountName();
        // check if user exist
        if(userRepository.findByUserName(userName) == null){
            throw new UserExceptionReq("User " + userName + " Not Exist");
        }

        //check if account with accName already exist
        if(checkIfUserHaveAccount(userName, accName)){
            throw new AccountExceptionReq("Account with name " + accName + " already exist.");
        }

        User user = userRepository.findByUserName(userName);
        account.setUser(user);

        //save
        accountRepository.save(account);

        return account;

    }

    //Getting all accounts
    public Iterable<Account> getAllAccounts(){

        return accountRepository.findAll();
    }

    //Add money to account
    public Account addMoney(String userName, String accName, float value){

        // check if user exist
        if(userRepository.findByUserName(userName) == null){
            throw new UserExceptionReq("User " + userName + " Not Exist");
        }

        //check if account exists
        if(!checkIfUserHaveAccount(userName, accName)){
            throw new AccountExceptionReq("User " + userName + " does not have account with name: " + accName);
        }

        //update value
        Account account = getAccountFromUser(userName,accName);

        float accountValue = account.getValue();
        account.setValue(accountValue + value);

        //save account
        accountRepository.save(account);

        return account;
    }

    //send money to another account
    /*
    userNameRec - user who is receiving money
    accNAmeRec - account name where money will be sent
    type - type of account
    accName - name of account from where money will be sent
     */
    @Transactional
    public Iterable<Account> sendMoneyToAcc(String userNameRec, String accNameRec, String userName, String accName, float value){

        // check if user exist
        if(userRepository.findByUserName(userNameRec) == null){
            throw new UserExceptionReq("User " + userNameRec + " Not Exist");
        }

        //check if accNameRec exist
        //check if userNameRec have accNameRec
        if(!checkIfUserHaveAccount(userNameRec, accNameRec)){
            throw new AccountExceptionReq(" User " + userNameRec + "does not have account with name " + accNameRec);
        }

        //check if accName exist
        //check if userName have accName
        if(!checkIfUserHaveAccount(userName, accName)){
            throw new AccountExceptionReq(" User " + userName + "does not have account with name " + accName);
        }
        
        if(!checkIfUserHaveEnoughMoney(userName, accName, value)){
            throw new AccountExceptionReq("User " + userName + " does not have enough money.");
        }
        
        //get both accounts
        Account accountRec = getAccountFromUser(userNameRec, accNameRec);
        Account accountSen = getAccountFromUser(userName, accName);
        
        //send money
        sendAndUpdateAccounts(accountSen, accountRec, value);

        List<Account> updatedAccounts = new ArrayList<>();
        updatedAccounts.add(accountSen);
        updatedAccounts.add(accountRec);

        return updatedAccounts;

    }

    public String deleteAcc(String userName, String accName){
        if(!checkIfUserHaveAccount(userName, accName)){
            throw new AccountExceptionReq(" User " + userName + "does not have account with name " + accName);
        }

        Account account = getAccountFromUser(userName, accName);
        accountRepository.delete(account);

        return "Account "+ accName + " deleted.";
    }

    //check if user have account
    public boolean checkIfUserHaveAccount(String userName, String accName){

        Optional<Account> account =  accountRepository.findByAccountNameAndUserUserName(accName, userName);
        if(account.isEmpty()){
            return false;
        }
        return  true;

//        if(accountRepository.findByAccountName(accName) == null ){
//            return false;
//        }
//        List<Account> accounts = accountRepository.findByAccountName(accName);
//
//        for (Account account : accounts) {
//            if (account.getUser().getUserName().equals(userName)) {
//                return true;
//            }
//        }
//        return false;
    }

    //get account of specific user
    Account getAccountFromUser(String userName, String accName){

        Optional<Account> account =  accountRepository.findByAccountNameAndUserUserName(accName, userName);

        if(account.isEmpty()){
            return null;
        }
        return account.get();

//        if(accountRepository.findByAccountName(accName) == null ){
//            return null;
//        }
//        List<Account> accounts = accountRepository.findByAccountName(accName);
//
//        for (Account account : accounts) {
//            if (account.getUser().getUserName().equals(userName)) {
//                return account;
//            }
//        }
//        return null;
    }

    //check if user have enough money
    public boolean checkIfUserHaveEnoughMoney(String userName, String accName, float value){
        Account account = getAccountFromUser(userName, accName);
        return !(account.getValue() < value);
    }
    
    //send money to acc

    public void sendAndUpdateAccounts(Account accountSen, Account accountRec, float value){
        float valueSen = accountSen.getValue();
        float valueRec = accountRec.getValue();
        
        if(accountSen.getAccountType() == accountRec.getAccountType()){
            accountRec.setValue(valueRec + value);
            accountSen.setValue(valueSen - value);
        } else if (accountSen.getAccountType() == AccountType.KN) {
            float valueEur = value / knEur;

            accountRec.setValue(valueRec + valueEur);
            accountSen.setValue(valueSen - value);
        } else {
            float valueKn = value * knEur;

            accountRec.setValue(valueRec + valueKn);
            accountSen.setValue(valueSen - value);
        }


        accountRepository.save(accountRec);
        accountRepository.save(accountSen);
    }

}
