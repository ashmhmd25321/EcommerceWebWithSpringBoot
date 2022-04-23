package com.example.ecommerceweb.global;

import com.example.ecommerceweb.model.Branch;
import com.example.ecommerceweb.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;
    static {
        cart = new ArrayList<Product>();
    }
}
