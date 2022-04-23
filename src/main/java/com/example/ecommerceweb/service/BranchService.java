package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Branch;
import com.example.ecommerceweb.repository.BranchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    BranchesRepository branchesRepository;
    public List<Branch> getAllBranch() {
        return branchesRepository.findAll();
    }
    public void addBranch(Branch branch) {
        branchesRepository.save(branch);
    }
    public void removeBranchById(int id) {
        branchesRepository.deleteById(id);
    }
    public Optional<Branch> getBranchById(int id) {
        return branchesRepository.findById(id);
    }
}
