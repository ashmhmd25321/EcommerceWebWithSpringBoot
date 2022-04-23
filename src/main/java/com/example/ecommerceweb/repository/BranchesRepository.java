package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchesRepository extends JpaRepository<Branch, Integer> {
}
