package com.davidegiannetti.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean active;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @OneToMany(mappedBy = "user")
    private Set<Vote> voteList;
    @CreatedDate
    @Column(nullable = false)
    private LocalDate creationDate;
    @LastModifiedDate
    private LocalDateTime lastAccess;

    public User(String firstName, String lastName, String username, String email, String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

}
