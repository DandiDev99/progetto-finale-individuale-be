package com.davidegiannetti.controller;

import com.davidegiannetti.dto.state.InputStateDto;
import com.davidegiannetti.dto.state.OutputStateDto;
import com.davidegiannetti.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/state")
@RequiredArgsConstructor
public class StateController {

    private StateService stateService;

    @PostMapping
    public ResponseEntity<OutputStateDto> create(@RequestBody InputStateDto inputStateDto){
        return new ResponseEntity<>(stateService.create(inputStateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        stateService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
