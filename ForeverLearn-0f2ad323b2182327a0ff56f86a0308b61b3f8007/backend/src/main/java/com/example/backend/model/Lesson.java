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

    @Lob
    @JsonIgnore
    private Blob file;

    public Lesson() {
    }

    public Lesson(String nameLesson, String descriptionLesson) {
        this.nameLesson = nameLesson;
        this.descriptionLesson = descriptionLesson;
    }
}
