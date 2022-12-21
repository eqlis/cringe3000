package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.Jwt;
import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.model.entity.VerificationToken;
import com.cringe.cringe3000.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static com.cringe.cringe3000.util.Constants.MAX;
import static com.cringe.cringe3000.util.Constants.REQUIRED;

@Getter
@AllArgsConstructor
public class UserDTO {

    private final Long id;

    private final String email;

    @JsonProperty("login")
    private final String username;

    private final Role role;


    public static UserDTO from(User u){
        return new UserDTO(u.getId(), u.getEmail(), u.getUsername(), u.getRole());
    }

}
