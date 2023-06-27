package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.vote.InputVoteDto;
import com.davidegiannetti.dto.vote.OutputVoteDto;
import com.davidegiannetti.entity.Post;
import com.davidegiannetti.entity.User;
import com.davidegiannetti.entity.Vote;
import com.davidegiannetti.repository.PostRepository;
import com.davidegiannetti.repository.VoteRepository;
import com.davidegiannetti.service.VoteService;
import com.davidegiannetti.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final PrincipalUtil principal;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public OutputVoteDto vote(InputVoteDto inputVoteDto) {
        User user = principal.getUserByPrincipal();
        Post post = postRepository.findById(inputVoteDto.getIdPost()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post non trovato."));
        Vote finalVote = new Vote(inputVoteDto.isLike(), user, post);
        voteRepository.findByUserAndPost(user, post).ifPresent((vote) -> {
            if(vote.isLike() == finalVote.isLike()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hai già dato questo voto al post.");
            }else{
                finalVote.setLike(!finalVote.isLike());
            }
        });
        return modelMapper.map(voteRepository.save(finalVote), OutputVoteDto.class);
    }

    @Override
    public Set<OutputVoteDto> findAllFromPost(Long idPost) {
        return voteRepository.findByPost(postRepository.findById(idPost).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post non trovato."))).stream()
                .map(vote -> modelMapper.map(vote, OutputVoteDto.class)).collect(Collectors.toSet());
    }

    @Override
    public void delete(Long idPost) {
        User user = principal.getUserByPrincipal();
        Post post = postRepository.findById(idPost).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post non trovato."));
        voteRepository.delete(voteRepository.findByUserAndPost(user,post).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Non trovo il voto da eliminare.")));
    }

}
