package com.example.ecommerceweb.service;

import com.example.ecommerceweb.model.InventoryRequests;
import com.example.ecommerceweb.repository.InventoryRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryRequestService {
    @Autowired
    InventoryRequestRepository inventoryRequestRepository;

    public List<InventoryRequests> getAll(String keyword) {
            return inventoryRequestRepository.listAll(keyword);
    }

    public List<InventoryRequests> listPendingRequests(String keyword) {
        return inventoryRequestRepository.allRequests(keyword);
    }

    public List<InventoryRequests> listConfirmedRequests(String keyword) {
        return inventoryRequestRepository.allConfirmedRequests(keyword);
    }
}
