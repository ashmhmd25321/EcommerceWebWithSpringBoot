package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Employee;
import com.example.ecommerceweb.repository.SalesAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesAgentService {
    @Autowired
    SalesAgentRepository salesAgentRepository;
    public List<Employee> getAllEmployee() {
        return salesAgentRepository.findAll();
    }
    public void addEmployee(Employee employee) {
        salesAgentRepository.save(employee);
    }
    public void removeEmployeeById(int id) {
        salesAgentRepository.deleteById(id);
    }
    public Optional<Employee> getEmployeeById(int id) {
        return salesAgentRepository.findById(id);
    }
    public List<Employee> getAllSalesAgentsByBranch(String branch) {
        return salesAgentRepository.findAllByBranch(branch);
    }
    public List<Employee> listAll(String keyword) {
        if (keyword != null) {
            return salesAgentRepository.search(keyword);
        }
        return salesAgentRepository.findAll();
    }
}
