package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.tag.InputTagDto;
import com.davidegiannetti.dto.tag.OutputTagDto;
import com.davidegiannetti.entity.Post;
import com.davidegiannetti.entity.Tag;
import com.davidegiannetti.repository.PostRepository;
import com.davidegiannetti.repository.TagRepository;
import com.davidegiannetti.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    @Override
    public OutputTagDto create(InputTagDto inputTagDto) {
        return modelMapper.map(tagRepository.save(modelMapper.map(inputTagDto, Tag.class)), OutputTagDto.class);
    }

    @Override
    public Set<OutputTagDto> getFromIdPost(Long idPost) {
        Post post = postRepository.findById(idPost).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post non trovato."));
        return tagRepository.findAllByPost(post).stream().map(tag -> modelMapper.map(tag, OutputTagDto.class)).collect(Collectors.toSet());
    }

    @Override
    public void delete(Long id) {
        tagRepository.delete(tagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag non trovato.")));
    }

}
