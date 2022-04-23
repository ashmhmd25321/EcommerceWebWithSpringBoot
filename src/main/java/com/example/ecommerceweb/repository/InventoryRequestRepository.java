package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.InventoryRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRequestRepository extends JpaRepository<InventoryRequests, Integer> {
    @Query(value = "SELECT * FROM inventory_requests WHERE sales_agent LIKE %?1%", nativeQuery = true)
    List<InventoryRequests> listAll(String keyword);

    @Query(value = "SELECT * FROM inventory_requests WHERE branch LIKE %?1% AND confirmed_or_pending = 'Pending'", nativeQuery = true)
    List<InventoryRequests> allRequests(String keyword);

    @Query(value = "SELECT * FROM inventory_requests WHERE branch LIKE %?1% AND confirmed_or_pending = 'Confirmed'", nativeQuery = true)
    List<InventoryRequests> allConfirmedRequests(String keyword);
}
