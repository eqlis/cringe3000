package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.Person;
import com.cringe.cringe3000.model.entity.User;
import com.cringe.cringe3000.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.charset.StandardCharsets;

@Getter
@AllArgsConstructor
public class UserDTO {

    private final Long id;

    private final String email;

    @JsonProperty("login")
    private final String username;

    private final Role role;

    private final String name;

    private final String photo;

    public static UserDTO from(User u) {
        Person p = u.getPerson();
        return new UserDTO(
          u.getId(),
          u.getEmail(),
          u.getUsername(),
          u.getRole(),
          p.getSurname() + " " + p.getFirstName() + " " + p.getLastName(),
          new String(
            p.getPhotos().stream().filter(ph -> ph.getIndex().equals(p.getSelectedPhoto())).findFirst().get().getPhoto(),
            StandardCharsets.UTF_8));
    }

}
