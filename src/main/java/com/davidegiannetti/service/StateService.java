package com.davidegiannetti.service;

import com.davidegiannetti.dto.state.InputStateDto;
import com.davidegiannetti.dto.state.OutputStateDto;

public interface StateService {

    OutputStateDto create(InputStateDto inputStateDto);
    void delete(Long id);
    OutputStateDto byState(String state);

}
