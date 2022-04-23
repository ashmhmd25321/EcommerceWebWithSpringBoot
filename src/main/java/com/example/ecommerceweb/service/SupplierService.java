package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Supplier;
import com.example.ecommerceweb.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }

    public void addSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public void removeSupplierById(int id) {
        supplierRepository.deleteById(id);
    }

    public Optional<Supplier> getSupplierById(int id) {
        return supplierRepository.findById(id);
    }

    public List<Supplier> lisAll(String keyword) {
        if (keyword != null) {
            return supplierRepository.search(keyword);
        }
        return supplierRepository.findAll();
    }
}
