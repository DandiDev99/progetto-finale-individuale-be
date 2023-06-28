package com.davidegiannetti.controller;

import com.davidegiannetti.dto.vote.InputVoteDto;
import com.davidegiannetti.dto.vote.OutputVoteDto;
import com.davidegiannetti.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vote")
public class VoteController {

    private VoteService voteService;

    @PostMapping
    public ResponseEntity<OutputVoteDto> vote(@RequestBody InputVoteDto inputVoteDto){
        return new ResponseEntity<>(voteService.vote(inputVoteDto), HttpStatus.OK);
    }

    @DeleteMapping("/{idPost}")
    public ResponseEntity<Void> delete(@PathVariable Long idPost){
        //TODO
        return null;
    }

}
