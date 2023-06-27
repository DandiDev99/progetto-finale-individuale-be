package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.user.RegistrationUserDto;
import com.davidegiannetti.dto.user.UserOutputDto;
import com.davidegiannetti.entity.User;
import com.davidegiannetti.repository.UserRepository;
import com.davidegiannetti.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserOutputDto registration(RegistrationUserDto registrationUserDto) {
        User user = userRepository.save(modelMapper.map(registrationUserDto, User.class));
        user.setActive(true);
        return modelMapper.map(user, UserOutputDto.class);
    }

}
