package com.davidegiannetti.dto.post;

import com.davidegiannetti.dto.category.InputCategoryDto;
import com.davidegiannetti.dto.tag.InputTagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistationPostDto {

    private String title;
    private String body;
    private InputCategoryDto category;
    private Set<InputTagDto> Tags;

}
