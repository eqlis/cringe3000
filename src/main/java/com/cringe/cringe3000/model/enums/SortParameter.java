package com.cringe.cringe3000.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortParameter {

    NAME("NAME"),
    EXPERIENCE("EXPERIENCE"),
    DEGREE("DEGREE"),
    SUBJECT("SUBJECT");

    private final String displayName;

}
