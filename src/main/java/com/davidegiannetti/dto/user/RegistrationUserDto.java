package com.davidegiannetti.dto.user;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationUserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

}