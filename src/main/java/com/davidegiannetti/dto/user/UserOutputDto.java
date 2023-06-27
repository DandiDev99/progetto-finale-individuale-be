package com.davidegiannetti.dto.user;
import com.davidegiannetti.entity.Role;
import com.davidegiannetti.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOutputDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Set<Role> roles;
    private Set<Vote> votes;
    private LocalDate creationDate;
    private LocalDateTime lastAccess;

}
