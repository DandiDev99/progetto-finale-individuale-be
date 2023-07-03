package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.category.InputCategoryDto;
import com.davidegiannetti.dto.category.OutputCategoryDto;
import com.davidegiannetti.entity.Category;
import com.davidegiannetti.repository.CategoryRepository;
import com.davidegiannetti.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper modelmapper;
    private final CategoryRepository categoryRepository;

    @Override
    public OutputCategoryDto create(InputCategoryDto inputCategoryDto) {
        return modelmapper.map(categoryRepository.save(modelmapper.map(inputCategoryDto, Category.class)), OutputCategoryDto.class);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
