package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.Person;
import com.cringe.cringe3000.model.enums.Gender;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;


@AllArgsConstructor
public class PersonBase {
    private final Long id;

    private final String photo;

    private final String lastName;

    private final String firstName;

    private final String surname;

    private final Gender gender;

    private final LocalDate birthday;

    private final String phone;

    public static PersonBase from(Person p){
        return new PersonBase(
                p.getId(),
                p.getPhoto()== null ? null : new String(p.getPhoto(), StandardCharsets.UTF_8),
                p.getLastName(),
                p.getFirstName(),
                p.getSurname(),
                p.getGender(),
                p.getBirthday(),
                p.getPhone());
    }

}
