package com.example.ecommerceweb.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
public class Supplier {
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

    public Supplier(Supplier supplier) {
        this.name = supplier.getName();
        this.email = supplier.getEmail();
        this.branch = supplier.getBranch();
        this.address = supplier.getAddress();
        this.password = supplier.getPassword();
        this.roles = supplier.getRoles();
        this.gender = supplier.getGender();
        this.mobileNumber = supplier.getMobileNumber();
    }

    public Supplier() {
    }
}
