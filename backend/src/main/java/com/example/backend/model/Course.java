package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    @ManyToOne
    private Category category;

    private String difficulty;

    @Lob
    @JsonIgnore
    private Blob picture;

    @Lob
    @JsonIgnore
    private Blob trailer;

    @ElementCollection
    private List<String> objectives;

    @ElementCollection
    private List<String> requirements;

    private Integer length;

    @OneToMany
    private List<Theme> themes;

    @ManyToOne
    private User author;

    public Course() {
    }

    public Course(String name, String description, Category category, String difficulty, Integer length, User author) {
        super();
        this.name = name;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.length = length;
        this.author = author;
    }

    public void addUserCourse(Theme theme){
        if(Objects.isNull(this.themes)){
            this.themes = new LinkedList<>();
        }
        this.themes.add(theme);
    }

    public void deleteTheme(Theme theme) {
        this.themes.remove(theme);
    }
}
