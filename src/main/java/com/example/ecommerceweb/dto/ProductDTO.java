package com.example.ecommerceweb.dto;

import com.example.ecommerceweb.model.Branch;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class ProductDTO {

    private Long id;

    private String name;
    private double price;
    private String description;

    private int branchId;

    private String imageName;
    private int quantity;
}
