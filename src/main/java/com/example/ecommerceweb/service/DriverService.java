package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Driver;
import com.example.ecommerceweb.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;

    public List<Driver> getAllDriver() {
        return driverRepository.findAll();
    }

    public void addDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public void removeDriver(int id) {
        driverRepository.deleteById(id);
    }

    public Optional<Driver> getDriverById(int id) {
        return driverRepository.findById(id);
    }

    public List<Driver> lisAll(String keyword) {
        if (keyword != null) {
            return driverRepository.search(keyword);
        }
        return driverRepository.findAll();
    }

    public List<Driver> getAll(String keyword) {
            return driverRepository.search(keyword);
    }
}
