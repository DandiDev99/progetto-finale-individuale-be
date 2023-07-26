package com.davidegiannetti.dto.comment;

import com.davidegiannetti.dto.user.UserOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OutputCommentDto {

    private Long id;
    private String content;
    private UserOutputDto user;

}
