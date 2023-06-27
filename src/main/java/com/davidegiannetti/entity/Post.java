package com.davidegiannetti.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String body;
    @Column(nullable = false)
    @ManyToOne
    private User author;
    @Column(nullable = false)
    @ManyToOne
    private Category category;
    @ManyToMany
    private Set<Tag> tags;
    @OneToMany(mappedBy = "post")
    private Set<Vote> votes;
    @ManyToOne
    private State state;

    public Post(String title, String body, User author, Category category) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.category = category;
    }

}
