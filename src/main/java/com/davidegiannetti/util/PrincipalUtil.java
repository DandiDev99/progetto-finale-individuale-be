package com.davidegiannetti.util;

import com.davidegiannetti.entity.User;
import com.davidegiannetti.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrincipalUtil {

    private final UserRepository userRepository;

    public User getUserByPrincipal(){
        return userRepository.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).get();
    }

}
