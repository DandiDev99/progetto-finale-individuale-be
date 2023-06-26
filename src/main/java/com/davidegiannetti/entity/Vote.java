package com.davidegiannetti.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueUserAndPost", columnNames = {"userId","postId"})
})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private boolean like;
    @Column(name = "userId")
    @ManyToOne
    private User user;
    @Column(name = "postId")
    @ManyToOne
    private Post post;

    public Vote(boolean like, User user, Post post) {
        this.like = like;
        this.user = user;
        this.post = post;
    }
}
