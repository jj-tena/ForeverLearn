package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Question {

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
    private List<Answer> answers;

    private int answersSize;

    @ManyToOne
    private Theme theme;

    @OneToOne
    private Answer bestAnswer;

    public Question(Participation participation, String title, String content, Theme theme) {
        this.participation = participation;
        this.title = title;
        this.content = content;
        this.likes = 0;
        this.views = 0;
        this.answers = new LinkedList<>();
        this.answersSize = 0;
        this.theme = theme;
    }

    public Question(){}

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

    public void addAnswer(Answer answer){
        this.answers.add(answer);
        this.answersSize++;
    }

    public void like() {
        this.likes++;
    }

    public void quitLike() {
        this.likes--;
    }

    public boolean isBestAnswer(Answer answer){
        return this.bestAnswer.equals(answer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                '}';
    }
}