package com.davidegiannetti.controller;

import com.davidegiannetti.dto.post.OutputPostDto;
import com.davidegiannetti.dto.post.RegistationPostDto;
import com.davidegiannetti.dto.vote.OutputVoteDto;
import com.davidegiannetti.service.PostService;
import com.davidegiannetti.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

    private final PostService postService;
    private final PrincipalUtil principalUtil;

    @PostMapping
    public ResponseEntity<OutputPostDto> create(@RequestBody RegistationPostDto registationPostDto){
        return new ResponseEntity<>(postService.create(registationPostDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OutputPostDto>> all(){
        return new ResponseEntity<>(postService.all(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputPostDto> findById(@PathVariable Long id){
        return new ResponseEntity<>(postService.byId(id), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<OutputPostDto>> findByTitle(@PathVariable String title){
        return new ResponseEntity<>(postService.byTitle(title), HttpStatus.OK);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<OutputPostDto> approve(@PathVariable Long id){
        return new ResponseEntity<>(postService.approve(id), HttpStatus.OK);
    }

    @PostMapping("/disapprove/{id}")
    public ResponseEntity<OutputPostDto> disapprove(@PathVariable Long id){
        return new ResponseEntity<>(postService.disapprove(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
