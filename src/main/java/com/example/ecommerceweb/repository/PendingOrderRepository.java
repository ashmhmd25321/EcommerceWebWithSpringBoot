package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.model.orders.PendingOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PendingOrderRepository extends JpaRepository<PendingOrders, Integer> {
    @Query(value = "SELECT * FROM pending_orders WHERE customer_email LIKE %?1% ORDER BY confirmed_or_pending DESC", nativeQuery = true)
    List<PendingOrders> search(String keyword);

    @Query(value = "SELECT * FROM pending_orders WHERE product_branch LIKE %?1% AND confirmed_or_pending='Pending' ORDER BY confirmed_or_pending DESC", nativeQuery = true)
    List<PendingOrders> getOrders(String keyword);

    @Query(value = "SELECT * FROM pending_orders WHERE product_branch LIKE %?1% AND completed='Completed'", nativeQuery = true)
    List<PendingOrders> getCompletedOrders(String keyword);

    @Query(value = "SELECT COUNT(completed) FROM pending_orders WHERE completed='Completed'", nativeQuery = true)
    public int getSales();

    @Query(value = "SELECT * FROM pending_orders WHERE product_branch LIKE %?1% AND confirmed_or_pending='Confirmed' ORDER BY confirmed_or_pending DESC", nativeQuery = true)
    List<PendingOrders> getConfirmedOrders(String keyword);
}
