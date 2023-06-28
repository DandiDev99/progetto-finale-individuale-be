package com.davidegiannetti.service;

import com.davidegiannetti.dto.tag.InputTagDto;
import com.davidegiannetti.dto.tag.OutputTagDto;

import java.util.Set;

public interface TagService {

    OutputTagDto create(InputTagDto inputTagDto);
    void delete(Long id);

}
