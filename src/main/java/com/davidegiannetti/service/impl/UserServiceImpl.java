package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.user.AuthenticationDto;
import com.davidegiannetti.dto.user.LoginUserDto;
import com.davidegiannetti.dto.user.RegistrationUserDto;
import com.davidegiannetti.dto.user.UserOutputDto;
import com.davidegiannetti.entity.User;
import com.davidegiannetti.repository.UserRepository;
import com.davidegiannetti.service.UserService;
import com.davidegiannetti.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;

    @Override
    public UserOutputDto registration(RegistrationUserDto registrationUserDto) {
        userRepository.findByUsername(registrationUserDto.getUsername()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username già utilizzato.");
        });
        userRepository.findByEmail(registrationUserDto.getEmail()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email già utilizzata.");
        });
        User user = modelMapper.map(registrationUserDto, User.class);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        //TODO : settare il ruolo user
        //user.setRoles(Set.of());
        //pezza perche non funziona il prepersist
        user.setCreationDate(LocalDate.now());
        return modelMapper.map(userRepository.save(user), UserOutputDto.class);
    }

    @Override
    public Set<UserOutputDto> getAll() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserOutputDto.class)).collect(Collectors.toSet());
    }

    @Override
    public UserOutputDto update(RegistrationUserDto userDto, Long id) {
        User user = modelMapper.map(userDto, User.class);
        user.setId(id);
        return modelMapper.map(userRepository.save(user), UserOutputDto.class);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public AuthenticationDto login(LoginUserDto loginUserDto) {
        User user = userRepository.findByUsername(loginUserDto.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nessun utente con questo username."));
        if (!user.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato: utente non attivo");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La password inserita non è valida");
        }
        user.setLastAccess(LocalDateTime.now());
        userRepository.save(user);
        try {
            UserOutputDto userOutputDto = modelMapper.map(user, UserOutputDto.class);
            Map<String, String> claimsPrivati = Map.of("user", objectMapper.writeValueAsString(userOutputDto));
            return new AuthenticationDto(jwtUtil.generate(userOutputDto.getEmail(), claimsPrivati), userOutputDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore lato server");
        }
    }

}
