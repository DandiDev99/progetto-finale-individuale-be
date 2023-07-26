package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.vote.InputVoteDto;
import com.davidegiannetti.dto.vote.OutputVoteDto;
import com.davidegiannetti.entity.Post;
import com.davidegiannetti.entity.User;
import com.davidegiannetti.entity.Vote;
import com.davidegiannetti.repository.PostRepository;
import com.davidegiannetti.repository.UserRepository;
import com.davidegiannetti.repository.VoteRepository;
import com.davidegiannetti.service.VoteService;
import com.davidegiannetti.util.PrincipalUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            if(vote.isLiked() == finalVote.isLiked()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hai già dato questo voto al post.");
            }else{
                finalVote.setLiked(!finalVote.isLiked());
            }
        });
        return modelMapper.map(voteRepository.save(finalVote), OutputVoteDto.class);
    }

    @Override
    public void delete(Long idPost) {
        User user = principal.getUserByPrincipal();
        Post post = postRepository.findById(idPost).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Post non trovato."));
        voteRepository.delete(voteRepository.findByUserAndPost(user,post).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Non trovo il voto da eliminare.")));
    }

    @Override
    public OutputVoteDto voted(Long idPost) {
        User user = principal.getUserByPrincipal();
        Post post = postRepository.findById(idPost).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Post non trovato."));
        return modelMapper.map(voteRepository.findByUserAndPost(user,post).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voto non trovato.")), OutputVoteDto.class);
    }

    @Override
    public OutputVoteDto update(InputVoteDto inputVoteDto) {
        User user = principal.getUserByPrincipal();
        Post post = postRepository.findById(inputVoteDto.getIdPost()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post non trovato."));
        Vote vote = voteRepository.findByUserAndPost(user,post).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voto non trovato."));
        vote.setLiked(inputVoteDto.isLike());
        return modelMapper.map(voteRepository.save(vote) , OutputVoteDto.class);
    }

}
