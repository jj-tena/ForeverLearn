package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Objective {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idObjective;

    @Column(columnDefinition = "TEXT")
    private String nameObjective;

    public Objective() {
    }

    public Objective(String nameObjective) {
        this.nameObjective = nameObjective;
    }
}
