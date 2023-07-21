package com.davidegiannetti.service;

import com.davidegiannetti.dto.validator.ValidatorInputDto;
import com.davidegiannetti.dto.validator.ValidatorOutputDto;

public interface ValidatorService {

    ValidatorOutputDto create (ValidatorInputDto validatorInputDto);
    ValidatorOutputDto update (Long id, ValidatorInputDto validatorInputDto);


}
