package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

    @Query(value = "SELECT * FROM driver WHERE branch LIKE %?1%", nativeQuery = true)
    List<Driver> search(String keyword);
}
