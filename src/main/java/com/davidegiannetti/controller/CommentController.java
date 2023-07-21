package com.davidegiannetti.controller;

import com.davidegiannetti.dto.comment.InputCommentDto;
import com.davidegiannetti.dto.comment.OutputCommentDto;
import com.davidegiannetti.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<OutputCommentDto> comment(@RequestBody InputCommentDto inputCommentDto) {
        return new ResponseEntity<>(commentService.comment(inputCommentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
