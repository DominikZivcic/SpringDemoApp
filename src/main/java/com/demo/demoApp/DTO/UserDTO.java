package com.demo.demoApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
public class UserDTO
{
    private String userName;
    private String role;


}
