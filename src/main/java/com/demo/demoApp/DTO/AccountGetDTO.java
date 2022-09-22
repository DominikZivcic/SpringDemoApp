package com.demo.demoApp.DTO;

import com.demo.demoApp.model.AccountType;
import com.demo.demoApp.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
public class AccountGetDTO
{

    private String accountName;
    private AccountType accountType;
    private float value;

}
