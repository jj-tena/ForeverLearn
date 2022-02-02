package com.example.backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Objective {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idObjective;

    private String nameObjective;

    public Objective() {
    }

    public Objective(String nameObjective) {
        this.nameObjective = nameObjective;
    }
}
