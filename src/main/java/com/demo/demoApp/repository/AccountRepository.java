package com.demo.demoApp.repository;

import com.demo.demoApp.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findByAccountName(String name);

    Optional<Account> findByAccountNameAndUserUserName(String accountName, String userName);

}
