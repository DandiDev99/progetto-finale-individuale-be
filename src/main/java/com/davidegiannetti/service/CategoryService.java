package com.davidegiannetti.service;

import com.davidegiannetti.dto.category.InputCategoryDto;
import com.davidegiannetti.dto.category.OutputCategoryDto;

public interface CategoryService {

    OutputCategoryDto create(InputCategoryDto inputCategoryDto);
    void delete(Long id);

}
