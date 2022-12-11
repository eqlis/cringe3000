package com.cringe.cringe3000.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse {

    private final List<PersonBase> personnel;
    private final long pagesAmount;

}
