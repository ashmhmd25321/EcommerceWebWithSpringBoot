package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.global.GlobalData;
import com.example.ecommerceweb.model.CustomUserDetail;
import com.example.ecommerceweb.model.Driver;
import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.model.orders.PendingOrders;
import com.example.ecommerceweb.repository.PendingOrderRepository;
import com.example.ecommerceweb.repository.UserRepository;
import com.example.ecommerceweb.service.BranchService;
import com.example.ecommerceweb.service.OrderService;
import com.example.ecommerceweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    BranchService branchService;
    @Autowired
    ProductService productService;
    @Autowired
    PendingOrderRepository pendingOrderRepository;
    @Autowired
    OrderService orderService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("branches", branchService.getAllBranch());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/branch/{id}")
    public String shopByBranch(Model model, @PathVariable int id) {
        model.addAttribute("branches", branchService.getAllBranch());
        model.addAttribute("products", productService.getAllProductsByBranchId(id));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "viewProduct";
    }

    @GetMapping("/shop/orders")
    public String viewOrders(Model model, @Param("keyword") String keyword) {
        List<PendingOrders> orders = orderService.lisAll(keyword);
        model.addAttribute("order", orders);
        model.addAttribute("keyword", keyword);
        return "orderView";
    }

    @PostMapping("/shop/orders/{id}")
    public String completeOrder(@PathVariable("id") int id, @ModelAttribute("order") PendingOrders pendingOrders, BindingResult result) {
        if (result.hasErrors()) {
            pendingOrders.setId(id);
            return "completeOrder";
        }
        pendingOrderRepository.save(pendingOrders);
        return "completeOrder";
    }
}
