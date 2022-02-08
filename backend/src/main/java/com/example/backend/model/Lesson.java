package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idLesson;

    private String nameLesson;

    @Column(columnDefinition = "TEXT")
    private String descriptionLesson;

    @Column(columnDefinition = "TEXT")
    private String iframeLesson;

    public Lesson() {
    }

    public Lesson(String nameLesson, String descriptionLesson, String iframeLesson) {
        this.nameLesson = nameLesson;
        this.descriptionLesson = descriptionLesson;
        this.iframeLesson = iframeLesson;
    }
}
