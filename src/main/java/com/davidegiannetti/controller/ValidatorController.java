package com.davidegiannetti.controller;

import com.davidegiannetti.dto.validator.ValidatorInputDto;
import com.davidegiannetti.dto.validator.ValidatorOutputDto;
import com.davidegiannetti.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/validator")
public class ValidatorController {

    private final ValidatorService validatorService;

    @PostMapping
    public ResponseEntity<ValidatorOutputDto> create(@RequestBody ValidatorInputDto validatorInputDto){
        return new ResponseEntity<>(validatorService.create(validatorInputDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValidatorOutputDto> update(@PathVariable Long id, @RequestBody ValidatorInputDto validatorInputDto){
        return new ResponseEntity<>(validatorService.update(id, validatorInputDto), HttpStatus.OK);
    }

}
