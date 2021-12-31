package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Entity
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

}
