package com.davidegiannetti.service;

import com.davidegiannetti.dto.comment.InputCommentDto;
import com.davidegiannetti.dto.comment.OutputCommentDto;

public interface CommentService {

    OutputCommentDto comment(InputCommentDto inputCommentDto);
    void delete(Long id);

}
