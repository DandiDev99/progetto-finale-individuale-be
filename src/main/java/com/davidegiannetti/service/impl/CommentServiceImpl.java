package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.comment.InputCommentDto;
import com.davidegiannetti.dto.comment.OutputCommentDto;
import com.davidegiannetti.entity.Comment;
import com.davidegiannetti.entity.Post;
import com.davidegiannetti.entity.User;
import com.davidegiannetti.repository.CommentRepository;
import com.davidegiannetti.repository.PostRepository;
import com.davidegiannetti.service.CommentService;
import com.davidegiannetti.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PrincipalUtil principal;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    public OutputCommentDto comment(InputCommentDto inputCommentDto) {
        User user = principal.getUserByPrincipal();
        Post post = postRepository.findById(inputCommentDto.getIdPost()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post da commentare non trovato."));
        Comment comment = new Comment(inputCommentDto.getContent(), user, post);
        return modelMapper.map(commentRepository.save(comment), OutputCommentDto.class);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

}
