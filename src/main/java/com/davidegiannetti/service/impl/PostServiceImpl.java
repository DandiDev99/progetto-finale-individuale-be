package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.post.OutputPostDto;
import com.davidegiannetti.dto.post.RegistationPostDto;
import com.davidegiannetti.entity.Post;
import com.davidegiannetti.entity.State;
import com.davidegiannetti.repository.PostRepository;
import com.davidegiannetti.service.PostService;
import com.davidegiannetti.service.StateService;
import com.davidegiannetti.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final PrincipalUtil principalUtil;
    private final StateService stateService;

    @Override
    public OutputPostDto create(RegistationPostDto registationPostDto) {
        Post post = modelMapper.map(registationPostDto, Post.class);
        post.setAuthor(principalUtil.getUserByPrincipal());
        post.setState(modelMapper.map(stateService.byState("APPROVAZIONE"), State.class));
        post.setVotes(Set.of());
        return modelMapper.map(postRepository.save(post), OutputPostDto.class);
    }

    @Override
    public List<OutputPostDto> all() {
        return postRepository.findAll().stream().map(post -> modelMapper.map(post, OutputPostDto.class)).toList();
    }

    @Override
    public OutputPostDto byId(Long id) {
        return modelMapper.map(postRepository.findById(id), OutputPostDto.class);
    }

    @Override
    public List<OutputPostDto> byTitle(String title) {
        return postRepository.findByTitle(title).stream().map(post -> modelMapper.map(post, OutputPostDto.class)).toList();
    }

    @Override
    public OutputPostDto approve(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post non trovato"));
        post.setState(modelMapper.map(stateService.byState("APPROVATO"), State.class));
        return modelMapper.map(postRepository.save(post), OutputPostDto.class);
    }

    @Override
    public OutputPostDto disapprove(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post non trovato"));
        post.setState(modelMapper.map(stateService.byState("DISAPPROVATO"), State.class));
        return modelMapper.map(postRepository.save(post), OutputPostDto.class);
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
