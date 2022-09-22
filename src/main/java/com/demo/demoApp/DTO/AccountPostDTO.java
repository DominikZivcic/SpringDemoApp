package com.demo.demoApp.DTO;

import com.demo.demoApp.model.AccountType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountPostDTO
{
    private String accountName;
    private AccountType accountType;
    private float value;

}
