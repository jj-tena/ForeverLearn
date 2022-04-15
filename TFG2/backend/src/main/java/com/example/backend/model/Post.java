package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User author;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean outstanding;

    private boolean interesting;

    private int likes;

    private int views;

    @OneToMany
    private List<Comment> comments;
}
