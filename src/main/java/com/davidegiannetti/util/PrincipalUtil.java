package com.davidegiannetti.util;

import com.davidegiannetti.entity.User;
import com.davidegiannetti.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
@Slf4j
public class PrincipalUtil {

    private final UserRepository userRepository;

    public User getUserByPrincipal(){
        log.info((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userRepository.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).get();
//.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "devi autenticarti per accedere a questo servizio.")
    }

}
