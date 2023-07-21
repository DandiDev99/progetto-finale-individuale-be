package com.davidegiannetti.dto.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidatorInputDto {

    private String fieldName;
    private Integer min;
    private Integer max;
    private Boolean specialChar;
    private Boolean upperCase;
    private Boolean lowerCase;
    private Boolean number;

}
