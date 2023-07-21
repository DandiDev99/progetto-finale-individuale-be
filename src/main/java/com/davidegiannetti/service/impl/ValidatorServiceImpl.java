package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.validator.ValidatorInputDto;
import com.davidegiannetti.dto.validator.ValidatorOutputDto;
import com.davidegiannetti.entity.Validator;
import com.davidegiannetti.repository.ValidatorRepository;
import com.davidegiannetti.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ValidatorServiceImpl implements ValidatorService {

    private final ValidatorRepository validatorRepository;
    private final ModelMapper modelMapper;

    @Override
    public ValidatorOutputDto create(ValidatorInputDto validatorInputDto) {
        return modelMapper.map(validatorRepository.save(modelMapper.map(validatorInputDto, Validator.class)), ValidatorOutputDto.class);
    }

    @Override
    public ValidatorOutputDto update(Long id,ValidatorInputDto validatorInputDto) {
        Validator v = modelMapper.map(validatorInputDto, Validator.class);
        v.setId(id);
        return modelMapper.map(validatorRepository.save(v), ValidatorOutputDto.class);
    }

}
