package com.davidegiannetti.controller;

import com.davidegiannetti.dto.category.InputCategoryDto;
import com.davidegiannetti.dto.category.OutputCategoryDto;
import com.davidegiannetti.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<OutputCategoryDto> create (@RequestBody InputCategoryDto inputCategoryDto){
        return new ResponseEntity<>(categoryService.create(inputCategoryDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete (@PathVariable Long id){
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
