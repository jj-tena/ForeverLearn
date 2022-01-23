package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

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

    @Lob
    @JsonIgnore
    private Blob picture;

    public User(){

    }

    public User(String name, String surname, String email, String password) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public User(String name, String surname, String email, String password, String description, String contact, String facebook, String twitter, String youtube, boolean isAdmin, Blob picture) {
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
        this.picture = picture;
    }
}
