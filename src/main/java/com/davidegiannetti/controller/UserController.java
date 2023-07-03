package com.davidegiannetti.controller;

import com.davidegiannetti.dto.user.AuthenticationDto;
import com.davidegiannetti.dto.user.LoginUserDto;
import com.davidegiannetti.dto.user.RegistrationUserDto;
import com.davidegiannetti.dto.user.UserOutputDto;
import com.davidegiannetti.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserOutputDto> registration (@RequestBody RegistrationUserDto registrationUserDto){
        return new ResponseEntity<>(userService.registration(registrationUserDto), HttpStatus.CREATED);
    }



    @GetMapping("/all")
    public ResponseEntity<Set<UserOutputDto>> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutputDto> update(@RequestBody RegistrationUserDto userDto, @PathVariable Long id){
        return new ResponseEntity<>(userService.update(userDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody LoginUserDto loginUserDto){
        return new ResponseEntity<>(userService.login(loginUserDto), HttpStatus.OK);
    }

}
