package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesAgentRepository extends JpaRepository<Employee, Integer> {
    Employee findEmployeeByEmail(String email);
    List<Employee> findAllByBranch(String branch);

    @Query(value = "SELECT * FROM employee WHERE branch LIKE %?1%", nativeQuery = true)
    public List<Employee> search(String keyword);
}
