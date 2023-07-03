package com.davidegiannetti.service;

import com.davidegiannetti.dto.post.OutputPostDto;
import com.davidegiannetti.dto.post.RegistationPostDto;

import java.util.List;

public interface PostService {

    OutputPostDto create (RegistationPostDto registationPostDto);
    List<OutputPostDto> all();
    OutputPostDto byId(Long id);
    List<OutputPostDto> byTitle(String title);
    OutputPostDto approve(Long id);
    OutputPostDto disapprove(Long id);
    void delete(Long id);

}
