package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idTheme;

    private String nameTheme;

    @Column(columnDefinition = "TEXT")
    private String descriptionTheme;

    @OneToMany
    private List<Lesson> lessons;

    public Theme() {
    }

    public Theme(String nameTheme, String descriptionTheme) {
        this.nameTheme = nameTheme;
        this.descriptionTheme = descriptionTheme;
    }

    public void addLesson(Lesson lesson){
        if(Objects.isNull(this.lessons)){
            this.lessons = new LinkedList<>();
        }
        this.lessons.add(lesson);
    }

    public void deleteLesson(Lesson lesson) {
        this.lessons.remove(lesson);
    }
}
