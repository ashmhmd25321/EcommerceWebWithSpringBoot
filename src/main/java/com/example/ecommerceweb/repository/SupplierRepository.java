package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Employee;
import com.example.ecommerceweb.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findSupplierByEmail(String email);

    @Query(value = "SELECT * FROM supplier WHERE branch LIKE %?1%", nativeQuery = true)
    List<Supplier> search(String keyword);
}
