package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.Driver;
import com.example.ecommerceweb.model.orders.PendingOrders;
import com.example.ecommerceweb.repository.PendingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    PendingOrderRepository pendingOrderRepository;

    public List<PendingOrders> lisAll(String keyword) {
            return pendingOrderRepository.search(keyword);
    }

    public List<PendingOrders> listOrders(String keyword) {
        return pendingOrderRepository.getOrders(keyword);
    }

    public List<PendingOrders> listCompletedOrders(String keyword) {
        return pendingOrderRepository.getCompletedOrders(keyword);
    }

    public Optional<PendingOrders> getOrderById(int id) {
        return pendingOrderRepository.findById(id);
    }

    public int listSales() {
        return pendingOrderRepository.getSales();
    }

    public List<PendingOrders> listConfirmedOrders(String keyword) {
        return pendingOrderRepository.getConfirmedOrders(keyword);
    }
}
