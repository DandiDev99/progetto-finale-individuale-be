package com.davidegiannetti.service.impl;

import com.davidegiannetti.dto.user.AuthenticationDto;
import com.davidegiannetti.dto.user.LoginUserDto;
import com.davidegiannetti.dto.user.RegistrationUserDto;
import com.davidegiannetti.dto.user.UserOutputDto;
import com.davidegiannetti.entity.Role;
import com.davidegiannetti.entity.User;
import com.davidegiannetti.entity.Validator;
import com.davidegiannetti.repository.RoleRepository;
import com.davidegiannetti.repository.UserRepository;
import com.davidegiannetti.repository.ValidatorRepository;
import com.davidegiannetti.service.EmailService;
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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ValidatorRepository validatorRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;
    private final EmailService emailService;

    @Override
    public UserOutputDto registration(RegistrationUserDto registrationUserDto) {
        //controlli preliminari
        userRepository.findByUsername(registrationUserDto.getUsername()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username già utilizzato.");
        });
        userRepository.findByEmail(registrationUserDto.getEmail()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email già utilizzata.");
        });

        User user = modelMapper.map(registrationUserDto, User.class);

        //validazione campo password
        Validator v = validatorRepository.findByFieldName("password").orElseGet(()->null);
        if(v != null){
            String pass = user.getPassword();
            if (v.getMin()!=null && v.getMin()>pass.length()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La password deve avere almeno "+v.getMin()+" caratteri!");
            }
            if (v.getMax()!=null && v.getMax()<pass.length()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La password deve avere al massimo "+v.getMax()+" caratteri!");
            }
            if (v.getSpecialChar()){
                if(!pass.matches(".*[^a-zA-Z0-9].*")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La password deve avere almeno un carattere speciale!");
                }
            }
            if(v.getUpperCase()){
                if(!pass.matches("[A-Z]")){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La password deve avere almeno un carattere MAIUSCOLO!");
                }
            }
            if(v.getLowerCase()){
                if(!pass.matches("[a-z]")){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La password deve avere almeno un carattere minuscolo!");
                }
            }
            if(v.getNumber()){
                if(!pass.matches("[0-9]")){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La password deve avere almeno un numero!");
                }
            }
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = generaPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setActive(true);
        user.setRoles(Set.of(roleRepository.findByAuthority("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")))));
        UserOutputDto saved = modelMapper.map(userRepository.save(user), UserOutputDto.class);
        emailService.sendConfirmationEmail(saved.getEmail(), password);
        return saved;
    }

    @Override
    public List<UserOutputDto> getAll() {
        return userRepository.findByDeleted(false).stream().map(user -> modelMapper.map(user, UserOutputDto.class)).toList();
    }

    @Override
    public UserOutputDto update(RegistrationUserDto userDto, Long id) {
        User user = modelMapper.map(userDto, User.class);
        user.setId(id);
        return modelMapper.map(userRepository.save(user), UserOutputDto.class);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato."));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void ban(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato."));
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public AuthenticationDto login(LoginUserDto loginUserDto) {
        User user = userRepository.findByUsername(loginUserDto.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nessun utente con questo username."));
        if (!user.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato: utente non attivo, per maggiori informazioni contattare l'assistenza.");
        }
        if(user.isDeleted()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato: questo account e' stato cancellato, non potrai mai piu accedervi.");
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

    private String generaPassword(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            char[] choices = new char[3];
            choices[0] = (char)random.nextInt('a','z');
            choices[1] = (char)random.nextInt('A','Z');
            choices[2] = (char)random.nextInt('0','9');
            sb.append(choices[random.nextInt(3)]);
        }
        return sb.toString();
    }

    @Override
    public void promoteStaff(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossibile trovare l'utente da promuovere."));
        Set<Role> newRoles = user.getRoles();
        newRoles.add(roleRepository.findByAuthority("ROLE_STAFF").orElseGet(() -> roleRepository.save(new Role("ROLE_STAFF"))));
        user.setRoles(newRoles);
        userRepository.save(user);
    }

    @Override
    public void unban(Long id) {
       User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato."));
       user.setActive(true);
       userRepository.save(user);
    }

}