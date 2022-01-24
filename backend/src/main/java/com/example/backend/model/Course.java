package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

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
    private List<Topic> topics;

    public Course() {
    }

    public Course(String name, String description, Category category, String difficulty, Integer length) {
        super();
        this.name = name;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.length = length;
    }
}
