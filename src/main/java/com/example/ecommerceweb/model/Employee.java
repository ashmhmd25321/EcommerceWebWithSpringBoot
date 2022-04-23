package com.example.ecommerceweb.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String name;
    @Column(nullable = false, unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;

    @NotEmpty
    private String branch;

    private String address;
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn (name = "ROLE_ID", referencedColumnName = "ID")}
    )
    private List<Role> roles;

    private String gender;
    private String mobileNumber;

    public Employee() {
    }

    public Employee(Employee employee) {
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.branch = employee.getBranch();
        this.address = employee.getAddress();
        this.password = employee.getPassword();
        this.roles = employee.getRoles();
        this.gender = employee.getGender();
        this.mobileNumber = employee.getMobileNumber();
    }
}
