package com.example.ecommerceweb.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "branch_id")
    private int id;

    private String branch;
}
