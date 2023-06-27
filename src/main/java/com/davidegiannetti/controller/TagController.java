package com.davidegiannetti.controller;

import com.davidegiannetti.dto.tag.InputTagDto;
import com.davidegiannetti.dto.tag.OutputTagDto;
import com.davidegiannetti.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Tag")
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<OutputTagDto> create(@RequestBody InputTagDto inputTagDto){
        return new ResponseEntity<>(tagService.create(inputTagDto), HttpStatus.OK);
    }

    @GetMapping("/{idPost}")
    public  ResponseEntity<Set<OutputTagDto>> getFromIdPost(@PathVariable Long idPost){
        return new ResponseEntity<>(tagService.getFromIdPost(idPost), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
