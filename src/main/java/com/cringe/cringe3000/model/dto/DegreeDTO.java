package com.cringe.cringe3000.model.dto;

import com.cringe.cringe3000.model.entity.Degree;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static com.cringe.cringe3000.util.Constants.MAX;
import static com.cringe.cringe3000.util.Constants.REQUIRED;

@Getter
@AllArgsConstructor
public class DegreeDTO {
    private final Long id;

    @NotEmpty(message = REQUIRED)
    @Size(max = 256, message = MAX)
    private final String name;

    public static DegreeDTO from(Degree d) {
        return new DegreeDTO(d.getId(), d.getName());
    }

    public Degree toDegree() {
        return new Degree(id, name);
    }
}
