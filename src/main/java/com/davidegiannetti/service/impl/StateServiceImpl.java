package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.state.InputStateDto;
import com.davidegiannetti.dto.state.OutputStateDto;
import com.davidegiannetti.entity.State;
import com.davidegiannetti.repository.StateRepository;
import com.davidegiannetti.service.StateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;
    private final ModelMapper modelMapper;

    @Override
    public OutputStateDto create(InputStateDto inputStateDto) {
        return modelMapper.map(stateRepository.save(modelMapper.map(inputStateDto, State.class)), OutputStateDto.class);
    }

    @Override
    public void delete(Long id) {
        stateRepository.delete(stateRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nessuno stato con questo id.")));
    }

}
