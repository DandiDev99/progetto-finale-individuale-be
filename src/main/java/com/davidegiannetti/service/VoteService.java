package com.davidegiannetti.service;

import com.davidegiannetti.dto.vote.InputVoteDto;
import com.davidegiannetti.dto.vote.OutputVoteDto;

import java.util.Set;

public interface VoteService {

    OutputVoteDto vote(InputVoteDto inputVoteDto);
    Set<OutputVoteDto> findAllFromPost (Long idPost);
    void delete(Long idPost);

}
