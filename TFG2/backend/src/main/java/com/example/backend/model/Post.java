package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Participation participation;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean outstanding;

    private boolean interesting;

    private int likes;

    private int views;

    @OneToMany
    private List<Comment> comments;

    private int commentsSize;

    @ManyToOne
    private Theme theme;

    public Post() {

    }

    public Post(Participation participation, String title, String content, Theme theme) {
        this.participation = participation;
        this.title = title;
        this.content = content;
        this.likes = 0;
        this.views = 0;
        this.comments = new LinkedList<>();
        this.commentsSize = 0;
        this.theme = theme;
    }


    public void setOutstanding() {
        this.outstanding = true;
    }

    public void setInteresting() {
        this.interesting = true;
    }

    public void resetInteresting() {
        this.interesting = false;
    }

    public void addView(){
        this.views++;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
        this.commentsSize++;
    }

    public void like() {
        this.likes++;
    }

    public void quitLike() {
        this.likes--;
    }
}
