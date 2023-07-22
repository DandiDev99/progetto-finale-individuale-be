package com.davidegiannetti.service;

import com.davidegiannetti.dto.user.AuthenticationDto;
import com.davidegiannetti.dto.user.LoginUserDto;
import com.davidegiannetti.dto.user.RegistrationUserDto;
import com.davidegiannetti.dto.user.UserOutputDto;

import java.util.Set;

public interface UserService {

    UserOutputDto registration(RegistrationUserDto registrationUserDto);
    Set<UserOutputDto> getAll();
    UserOutputDto update (RegistrationUserDto userDto, Long id);
    void delete(Long id);
    void ban(Long id);
    AuthenticationDto login (LoginUserDto loginUserDto);
    void promoteStaff(Long id);
    void unban(Long id);
}
