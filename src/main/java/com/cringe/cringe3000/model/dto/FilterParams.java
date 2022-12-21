package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.enums.SortParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class FilterParams {

    private final String name;
    private final Integer experience;
    private final Long degreeId;
    private final Long subjectId;
    private final SortParameter sortParam;
    private final boolean isAscending;

}
