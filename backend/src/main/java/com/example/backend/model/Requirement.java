package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idRequirement;

    private String nameRequirement;

    public Requirement() {
    }

    public Requirement(String nameRequirement) {
        this.nameRequirement = nameRequirement;
    }
}
