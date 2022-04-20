package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Course course;

    private int points;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Comment> comments;

    public Participation(User student, Course course) {
        this.student = student;
        this.course = course;
        this.points = 0;
        this.posts = new LinkedList<>();
        this.comments = new LinkedList<>();
    }

    public Participation() {

    }
}
