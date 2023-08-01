package com.davidegiannetti.entity;

import com.davidegiannetti.audit.UserAuditListner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(value = {UserAuditListner.class})
public class Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String fieldName;
    private Integer min;
    private Integer max;
    private Boolean specialChar;
    private Boolean upperCase;
    private Boolean lowerCase;
    private Boolean number;
    @LastModifiedDate
    private LocalDateTime lastModified;
}
