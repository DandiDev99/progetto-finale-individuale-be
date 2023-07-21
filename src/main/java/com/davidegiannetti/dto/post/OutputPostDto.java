package com.davidegiannetti.dto.post;

import com.davidegiannetti.dto.category.OutputCategoryDto;
import com.davidegiannetti.dto.comment.OutputCommentDto;
import com.davidegiannetti.dto.state.OutputStateDto;
import com.davidegiannetti.dto.tag.OutputTagDto;
import com.davidegiannetti.dto.user.UserOutputDto;
import com.davidegiannetti.dto.vote.OutputVoteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OutputPostDto {

    private Long id;
    private String title;
    private String body;
    private UserOutputDto author;
    private OutputCategoryDto category;
    private List<OutputTagDto> tags;
    private List<OutputVoteDto> votes;
    private OutputStateDto state;
    private UserOutputDto staffApprover;
    private List<OutputCommentDto> comments;

}
