package com.demo.demoApp.Mapper;

import com.demo.demoApp.model.Account;
import com.demo.demoApp.DTO.AccountGetDTO;
import com.demo.demoApp.DTO.AccountPostDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper
{
    AccountGetDTO toAccountGetDTO(Account account);

    Iterable<AccountGetDTO> toAccountGetDTOs(Iterable<Account> accounts);

    Account toAccount(AccountPostDTO accountPostDTO);
}
