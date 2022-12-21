package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private final UserDTO user;
    private final String jwt;

}
