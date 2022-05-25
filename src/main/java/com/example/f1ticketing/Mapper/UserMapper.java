package com.example.f1ticketing.Mapper;

import com.example.f1ticketing.DTO.RegisterDTO;
import com.example.f1ticketing.Model.User;

public class UserMapper {

    public User convertFromDTO(RegisterDTO registerDTO) {
        User user = new User();

        user.setPassword(registerDTO.getPassword());
        user.setUsername(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());

        return user;
    }
}
