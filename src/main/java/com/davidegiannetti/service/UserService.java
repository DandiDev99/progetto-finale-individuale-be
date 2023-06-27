package com.davidegiannetti.service;

import com.davidegiannetti.dto.user.RegistrationUserDto;
import com.davidegiannetti.dto.user.UserOutputDto;

public interface UserService {

    UserOutputDto registration(RegistrationUserDto registrationUserDto);

}
