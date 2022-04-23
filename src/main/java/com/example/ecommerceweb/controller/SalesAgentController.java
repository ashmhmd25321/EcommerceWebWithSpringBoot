package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.dto.ProductDTO;
import com.example.ecommerceweb.model.*;
import com.example.ecommerceweb.model.orders.PendingOrders;
import com.example.ecommerceweb.repository.InventoryRequestRepository;
import com.example.ecommerceweb.repository.PendingOrderRepository;
import com.example.ecommerceweb.repository.SalesAgentRepository;
import com.example.ecommerceweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SalesAgentController {

    @Autowired
    SalesAgentRepository salesAgentRepository;
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    OrderService orderService;
    @Autowired
    PendingOrderRepository pendingOrderRepository;
    @Autowired
    InventoryRequestService inventoryRequestService;
    @Autowired
    ProductService productService;
    @Autowired
    BranchService branchService;
    @Autowired
    DriverService driverService;
    @Autowired
    InventoryRequestRepository inventoryRequestRepository;

    @GetMapping("/salesAgent")
    public String salesAgentHome(@AuthenticationPrincipal CustomSalesAgentDetail customSalesAgentDetail, Model model) {
        String saleAgentEmail = customSalesAgentDetail.getUsername();
        Employee employee = salesAgentRepository.findEmployeeByEmail(saleAgentEmail);

        model.addAttribute("employee", employee);
        return "salesAgentHome";
    }

    @GetMapping("/salesAgent/orders")
    public String viewOrders(Model model, @Param("keyword") String keyword) {
        List<PendingOrders> orders = orderService.listOrders(keyword);
        model.addAttribute("orders", orders);
        model.addAttribute("keyword", keyword);
        return "viewOrderToAgent";
    }

    @GetMapping("/salesAgent/confirmedOrders")
    public String viewConfirmedOrders(Model model, @Param("keyword") String keyword) {
        List<PendingOrders> orders = orderService.listConfirmedOrders(keyword);
        model.addAttribute("orders", orders);
        model.addAttribute("keyword", keyword);
        return "viewConfirmedOrders";
    }

    @GetMapping("/salesAgent/drivers")
    public String viewDrivers(Model model, @Param("keyword") String keyword) {
        List<Driver> drivers = driverService.getAll(keyword);
        model.addAttribute("employees", drivers);
        model.addAttribute("keyword", keyword);
        return "viewVehicleToAgent";
    }

    @GetMapping("/salesAgent/requests/add")
    public String getAddRequests(Model model) {
        model.addAttribute("request", new InventoryRequests());
        return "inventoryStockRequestAdd";
    }

    @PostMapping("/salesAgent/request/add")
    public String addRequest(@ModelAttribute("request") InventoryRequests inventoryRequests, HttpServletRequest request) throws ServletException {
        inventoryRequestRepository.save(inventoryRequests);
        return "requestAdded";
    }

    @GetMapping("/salesAgent/inventoryRequests")
    public String viewRequests(Model model, @Param("keyword") String keyword) {
        List<InventoryRequests> inventoryRequests = inventoryRequestService.getAll(keyword);
        model.addAttribute("requests", inventoryRequests);
        model.addAttribute("keyword", keyword);
        return "viewRequests";
    }

    @GetMapping("/salesAgent/completedOrders")
    public String viewCompletedOrders(Model model, @Param("keyword") String keyword) {
        List<PendingOrders> orders = orderService.listCompletedOrders(keyword);
        int sales = orderService.listSales();
        model.addAttribute("sale", sales);
        model.addAttribute("orders", orders);
        model.addAttribute("keyword", keyword);
        return "viewCompletedOrders";
    }

    @GetMapping("/salesAgent/products")
    public String viewProducts(Model model, @Param("keyword") String keyword) {
        List<Product> products = productService.listProductsByBranch(keyword);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "viewProductToAgents";
    }

    @PostMapping("/salesAgent/orders/{id}")
    public String updateOrder(@PathVariable("id") int id, @ModelAttribute("order") PendingOrders pendingOrders, BindingResult result, Model model, @Param("keyword") String keyword) {
        List<PendingOrders> orders = orderService.listOrders(keyword);
        model.addAttribute("orders", orders);
        model.addAttribute("keyword", keyword);
        if (result.hasErrors()) {
            pendingOrders.setId(id);
            return "viewOrderToAgent";
        }
        pendingOrderRepository.save(pendingOrders);
        return "orderUpdated";
    }

    @PostMapping("/salesAgent/products/update/{id}")
    public String updateProducts(@PathVariable("id") Long id, @ModelAttribute("product") Product product, BindingResult result, Model model, @Param("keyword") String keyword) {
        if (result.hasErrors()) {
            product.setId(id);
            return "viewProductsToAgent";
        }
        productService.addProduct(product);
        return "productUpdated";
    }

    @GetMapping("/salesAgent/products/add")
    public String getAddProducts(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("branches", branchService.getAllBranch());
        return "agentProductsAdd";
    }

    @PostMapping("/salesAgent/products/add")
    public String addproduct(@ModelAttribute("productDTO")ProductDTO productDTO,
                             @RequestParam("productImage") MultipartFile file,
                             @RequestParam("imgName") String imgName) throws IOException {

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setBranch(branchService.getBranchById(productDTO.getBranchId()).get());
        product.setQuantity(productDTO.getQuantity());

        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else {
            imageUUID = imgName;
        }

        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "successfullyProductAdded";
    }

    @PostMapping("/salesAgent/confirmedOrders/{id}")
    public String updateConfirmedOrder(@PathVariable("id") int id, @ModelAttribute("order") PendingOrders pendingOrders, BindingResult result, Model model, @Param("keyword") String keyword) {
        List<PendingOrders> orders = orderService.listOrders(keyword);
        model.addAttribute("orders", orders);
        model.addAttribute("keyword", keyword);
        if (result.hasErrors()) {
            pendingOrders.setId(id);
            return "salesAgentHome";
        }
        pendingOrderRepository.save(pendingOrders);
        return "confirmedOrderUpdated";
    }

    @GetMapping("/salesAgent/orders/delete/{id}")
    public String deleteOrder(@PathVariable int id) {
        pendingOrderRepository.deleteById(id);
        return "orderDeleted";
    }

    @GetMapping("/salesAgent/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.removeProductById(id);
        return "productDeleted";
    }

    @GetMapping("/salesAgent/requests/delete/{id}")
    public String deleteRequests(@PathVariable int id) {
        inventoryRequestRepository.deleteById(id);
        return "requestDeleted";
    }

    @GetMapping("/salesAgent/completedOrders/delete/{id}")
    public String deleteCompletedOrder(@PathVariable int id) {
        pendingOrderRepository.deleteById(id);
        return "completedOrderDeleted";
    }

    @GetMapping("/salesAgent/confirmedOrders/delete/{id}")
    public String deleteConfirmedOrder(@PathVariable int id) {
        pendingOrderRepository.deleteById(id);
        return "confirmedOrderDeleted";
    }
}