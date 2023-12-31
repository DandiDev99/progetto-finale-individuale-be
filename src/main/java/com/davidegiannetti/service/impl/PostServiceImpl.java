package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.post.OutputPostDto;
import com.davidegiannetti.dto.post.RegistationPostDto;
import com.davidegiannetti.dto.vote.OutputVoteDto;
import com.davidegiannetti.entity.*;
import com.davidegiannetti.repository.*;
import com.davidegiannetti.service.PostService;
import com.davidegiannetti.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final PrincipalUtil principalUtil;
    private final StateRepository stateRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final ValidatorRepository validatorRepository;

    @Override
    public OutputPostDto create(RegistationPostDto registationPostDto) {
        Post post = modelMapper.map(registationPostDto, Post.class);
        Validator v = validatorRepository.findByFieldName("titolo").orElseGet(()->null);
        if(v!=null){
            if(v.getMin()!=null && v.getMin()>post.getTitle().length()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il titolo del tuo post deve contenere almeno "+v.getMin()+" caratteri!");
            }
            if(v.getMax()!=null && v.getMax()<post.getTitle().length()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il titolo del tuo post deve contenere al massimo "+v.getMin()+" caratteri!");
            }
        }
        post.setCategory(categoryRepository.findByName(post.getCategory().getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria non valida")));
        //cerco i tag per nome se non ci sono li creo e li aggiungo
        post.setTags(registationPostDto.getTags().stream().map( tagName -> tagRepository.findByName(tagName).orElseGet(() -> tagRepository.save(new Tag(tagName)))).collect(Collectors.toSet()));
        post.setAuthor(principalUtil.getUserByPrincipal());
        post.setState(stateRepository.findByState("APPROVAZIONE").orElseGet(() -> stateRepository.save(new State("APPROVAZIONE"))));
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
        post.setState(stateRepository.findByState("APPROVATO").orElseGet(() -> stateRepository.save(new State("APPROVATO"))));
        post.setStaffApprover(principalUtil.getUserByPrincipal());
        return modelMapper.map(postRepository.save(post), OutputPostDto.class);
    }

    @Override
    public OutputPostDto disapprove(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post non trovato"));
        post.setState(stateRepository.findByState("DISAPPROVATO").orElseGet(() -> stateRepository.save(new State("DISAPPROVATO"))));
        post.setStaffApprover(principalUtil.getUserByPrincipal());
        return modelMapper.map(postRepository.save(post), OutputPostDto.class);
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public OutputVoteDto voted(Long idPost, Long idUser) {
        return null;
    }

}
