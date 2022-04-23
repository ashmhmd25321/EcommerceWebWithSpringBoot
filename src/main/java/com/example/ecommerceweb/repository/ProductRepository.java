package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.Branch;
import com.example.ecommerceweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByBranch_Id(int id);

    @Query(value = "SELECT * FROM product WHERE branch_id LIKE %?1%", nativeQuery = true)
    List<Product> search(String keyword);

    @Query(value = "SELECT * FROM product INNER JOIN branch ON product.branch_id = branch.branch_id WHERE branch.branch LIKE %?1%", nativeQuery = true)
    List<Product> getProductsByBranch(String keyword);
}
