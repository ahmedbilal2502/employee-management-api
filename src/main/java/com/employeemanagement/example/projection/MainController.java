package com.employeemanagement.example.projection;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(name = "allow")
@AllArgsConstructor
public class MainController {

    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/products")
    public List<ProductProjection> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/orders")
    public List<OrderProjection> getOrders() {
        System.out.println("Orders");
        return orderService.getAllOrders();
    }
}
