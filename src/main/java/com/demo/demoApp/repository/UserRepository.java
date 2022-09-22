package com.demo.demoApp.repository;

import com.demo.demoApp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String name);
}
