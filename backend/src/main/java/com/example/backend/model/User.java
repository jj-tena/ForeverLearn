package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String description;

    private String contact;

    private String facebook;

    private String twitter;

    private String youtube;

    private boolean isAdmin;

    @OneToMany
    private List<Course> userCourses;

    @OneToMany
    private List<Course> completedCourses;

    @OneToMany
    private List<Course> enrolledCourses;

    @OneToMany
    private List<Course> wishedCourses;

    @Lob
    @JsonIgnore
    private Blob profilePhoto;

    public User(){

    }

    public User(String name, String surname, String email, String password) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public User(String name, String surname, String email, String password, String description, String contact, String facebook, String twitter, String youtube, boolean isAdmin, Blob profilePhoto) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.description = description;
        this.contact = contact;
        this.facebook = facebook;
        this.twitter = twitter;
        this.youtube = youtube;
        this.isAdmin = isAdmin;
        this.profilePhoto = profilePhoto;
    }
}
