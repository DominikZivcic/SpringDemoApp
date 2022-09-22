package com.demo.demoApp.model;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String accountName;
    private AccountType accountType;
    private float value;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
