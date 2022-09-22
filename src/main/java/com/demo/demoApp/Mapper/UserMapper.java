package com.demo.demoApp.Mapper;

import com.demo.demoApp.DTO.UserDTO;
import com.demo.demoApp.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    UserDTO toUserDTO(User user);

    Iterable<UserDTO> toUserDTOs(Iterable<User> users);

    User toUser(UserDTO userDTO);
}
