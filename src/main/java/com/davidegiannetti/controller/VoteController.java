package com.davidegiannetti.controller;

import com.davidegiannetti.dto.vote.InputVoteDto;
import com.davidegiannetti.dto.vote.OutputVoteDto;
import com.davidegiannetti.service.VoteService;
import com.davidegiannetti.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<OutputVoteDto> vote(@RequestBody InputVoteDto inputVoteDto){
        return new ResponseEntity<>(voteService.vote(inputVoteDto), HttpStatus.OK);
    }

    @GetMapping("/{idPost}")
    public ResponseEntity<OutputVoteDto> voted(@PathVariable Long idPost){
        return new ResponseEntity<>(voteService.voted(idPost), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<OutputVoteDto> update(@RequestBody InputVoteDto inputVoteDto){
        return new ResponseEntity<>(voteService.update(inputVoteDto), HttpStatus.OK);
    }

    @PostMapping("/delete/{idPost}")
    public ResponseEntity<Void> delete(@PathVariable Long idPost){
        voteService.delete(idPost);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
