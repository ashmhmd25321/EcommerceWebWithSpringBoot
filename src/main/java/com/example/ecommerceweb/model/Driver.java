package com.example.ecommerceweb.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String name;

    @NotEmpty
    private String branch;

    private String address;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn (name = "ROLE_ID", referencedColumnName = "ID")}
    )
    private List<Role> roles;

    private String gender;
    private String mobileNumber;
    private String vehicle;
    private String vehicleNo;

    public Driver() {
    }

    public Driver(Driver driver) {
        this.name = driver.getName();
        this.branch = driver.getBranch();
        this.address = driver.getAddress();
        this.roles = driver.getRoles();
        this.gender = driver.getGender();
        this.mobileNumber = driver.getMobileNumber();
        this.vehicle = driver.getVehicle();
        this.vehicleNo = driver.getVehicleNo();
    }
}
