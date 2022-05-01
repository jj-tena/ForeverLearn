package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Participation participation;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean outstanding;

    private int likes;


    public Comment(){}

    public Comment(Participation participation, String content) {
        this.participation = participation;
        this.content = content;
        this.outstanding = false;
        this.likes = 0;
    }

    public void setOutstanding(){
        this.outstanding = true;
    }

    public void like() {
        this.likes++;
    }

    public void quitLike() {
        this.likes--;
    }

}
