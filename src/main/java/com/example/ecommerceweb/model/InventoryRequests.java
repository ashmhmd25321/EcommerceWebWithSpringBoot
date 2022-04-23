package com.example.ecommerceweb.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class InventoryRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String salesAgent;

    private String requestStock;

    private String branch;

    private String confirmedOrPending;

    private String confirmedBy;
}
