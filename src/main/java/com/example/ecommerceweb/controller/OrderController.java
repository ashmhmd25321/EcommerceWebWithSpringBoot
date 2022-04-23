package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.global.GlobalData;
import com.example.ecommerceweb.model.CustomUserDetail;
import com.example.ecommerceweb.model.Product;
import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.model.orders.PendingOrders;
import com.example.ecommerceweb.repository.PendingOrderRepository;
import com.example.ecommerceweb.repository.UserRepository;
import com.example.ecommerceweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;

@Controller
public class OrderController {
    @Autowired
    ProductService productService;
    @Autowired
    PendingOrderRepository pendingOrderRepository;

    @GetMapping("/orderNow/{id}")
    public String getOrderNow(Model model, @PathVariable Long id) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("product", productService.getProductById(id).get());
        Product product = new Product();
        return "placeOrder";
    }

    @PostMapping("/orderNow/{id}")
    public String addPendingOrder(@ModelAttribute("order")PendingOrders pendingOrders, HttpServletRequest request) throws ServletException {
        pendingOrderRepository.save(pendingOrders);
        return "redirect:/orderNow/{id}";
    }
}
