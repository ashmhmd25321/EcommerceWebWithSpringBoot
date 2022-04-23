package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Product;
import com.example.ecommerceweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public void addProduct(Product product) {
        productRepository.save(product);
    }
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public List<Product> getAllProductsByBranchId(int id) {
        return productRepository.findAllByBranch_Id(id);
    }

    public List<Product> lisAll(String keyword) {
        if (keyword != null) {
            return productRepository.search(keyword);
        }
        return productRepository.findAll();
    }

    public List<Product> listProductsByBranch(String keyword) {
        return productRepository.getProductsByBranch(keyword);
    }

}
