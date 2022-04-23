package com.example.ecommerceweb.controller;


import com.example.ecommerceweb.model.CustomSupplierDetails;
import com.example.ecommerceweb.model.Driver;
import com.example.ecommerceweb.model.InventoryRequests;
import com.example.ecommerceweb.model.Supplier;
import com.example.ecommerceweb.model.orders.PendingOrders;
import com.example.ecommerceweb.repository.InventoryRequestRepository;
import com.example.ecommerceweb.repository.SupplierRepository;
import com.example.ecommerceweb.service.DriverService;
import com.example.ecommerceweb.service.InventoryRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SupplierController {
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    InventoryRequestService inventoryRequestService;
    @Autowired
    InventoryRequestRepository inventoryRequestRepository;
    @Autowired
    DriverService driverService;

    @GetMapping("/supplier")
    public String supplierHome(@AuthenticationPrincipal CustomSupplierDetails customSupplierDetails, Model model) {
        String supplierEmail = customSupplierDetails.getUsername();
        Supplier supplier = supplierRepository.findSupplierByEmail(supplierEmail);

        model.addAttribute("employee", supplier);
        return "supplierHome";
    }

    @GetMapping("/supplier/requests")
    public String viewRequests(Model model, @Param("keyword") String keyword) {
        List<InventoryRequests> inventoryRequests = inventoryRequestService.listPendingRequests(keyword);
        model.addAttribute("requests", inventoryRequests);
        model.addAttribute("keyword", keyword);
        return "viewPendingRequests";
    }

    @GetMapping("/supplier/confirmedRequests")
    public String viewConfirmedRequests(Model model, @Param("keyword") String keyword) {
        List<InventoryRequests> inventoryRequests = inventoryRequestService.listConfirmedRequests(keyword);
        model.addAttribute("requests", inventoryRequests);
        model.addAttribute("keyword", keyword);
        return "viewConfirmedRequests";
    }

    @PostMapping("/supplier/requests/{id}")
    public String updateRequest(@PathVariable("id") int id, @ModelAttribute("order") InventoryRequests inventoryRequests, BindingResult result, Model model, @Param("keyword") String keyword) {
        List<InventoryRequests> inventoryRequests1 = inventoryRequestService.listPendingRequests(keyword);
        model.addAttribute("request", inventoryRequests1);
        model.addAttribute("keyword", keyword);

        inventoryRequestRepository.save(inventoryRequests);
        return "requestUpdated";
    }

    @GetMapping("/supplier/requests/delete/{id}")
    public String deleteRequests(@PathVariable int id) {
        inventoryRequestRepository.deleteById(id);
        return "requestUpdated";
    }

    @GetMapping("/supplier/drivers")
    public String viewDrivers(Model model, @Param("keyword") String keyword) {
        List<Driver> drivers = driverService.getAll(keyword);
        model.addAttribute("employees", drivers);
        model.addAttribute("keyword", keyword);
        return "vehicleToSuppliers";
    }

    @GetMapping("/supplier/confirmedRequests/delete/{id}")
    public String deleteConfirmedRequests(@PathVariable int id) {
        inventoryRequestRepository.deleteById(id);
        return "confirmedRequestUpdated";
    }
}
