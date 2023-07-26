package com.davidegiannetti.service;

import com.davidegiannetti.dto.vote.InputVoteDto;
import com.davidegiannetti.dto.vote.OutputVoteDto;

import java.util.Set;

public interface VoteService {

    OutputVoteDto vote(InputVoteDto inputVoteDto);
    void delete(Long idPost);
    OutputVoteDto voted(Long idPost);
    OutputVoteDto update(InputVoteDto inputVoteDto);
}
