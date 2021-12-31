package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

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

}
