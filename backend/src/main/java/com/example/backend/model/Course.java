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

    @Column(columnDefinition = "TEXT")
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

    @OneToMany
    private List<Objective> objectives;

    @OneToMany
    private List<Requirement> requirements;

    private Integer length;

    @OneToMany
    private List<Theme> themes;

    @ManyToOne
    private User author;

    private Integer likes = 0;

    private Integer dislikes = 0;

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

    public void addTheme(Theme theme){
        if(Objects.isNull(this.themes)){
            this.themes = new LinkedList<>();
        }
        this.themes.add(theme);
    }

    public void deleteTheme(Theme theme) {
        this.themes.remove(theme);
    }

    public void addObjective(Objective objective){
        if(Objects.isNull(this.objectives)){
            this.objectives = new LinkedList<>();
        }
        this.objectives.add(objective);
    }

    public void deleteObjective(Objective objective) {
        this.objectives.remove(objective);
    }

    public void addRequirement(Requirement requirement){
        if(Objects.isNull(this.requirements)){
            this.requirements = new LinkedList<>();
        }
        this.requirements.add(requirement);
    }

    public void deleteRequirement(Requirement requirement) {
        this.requirements.remove(requirement);
    }

    public void like(){
        this.likes++;
    }

    public void quitLike(){
        this.likes--;
    }

    public void dislike(){
        this.dislikes++;
    }

    public void quitDislike(){
        this.dislikes--;
    }

}
