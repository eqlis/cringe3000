package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.Person;
import com.cringe.cringe3000.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

import static com.cringe.cringe3000.util.Constants.DATE_FORMAT;

@Getter
@AllArgsConstructor
public class PersonBase {
    private final Long id;

    private final PhotoInfo photoInfo;

    private final String lastName;

    private final String firstName;

    private final String surname;

    private final Gender gender;

    @JsonFormat(pattern = DATE_FORMAT)
    private final LocalDate birthday;

    private final String phone;

    public static PersonBase from(Person p){
        return new PersonBase(
                p.getId(),
                new PhotoInfo(p.getSelectedPhoto(), p.getPhotosSize()),
                p.getLastName(),
                p.getFirstName(),
                p.getSurname(),
                p.getGender(),
                p.getBirthday(),
                p.getPhone());
    }

}
