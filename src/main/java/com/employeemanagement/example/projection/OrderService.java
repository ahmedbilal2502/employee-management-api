package com.employeemanagement.example.projection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderProjection> getAllOrders() {
        return orderRepository.findAllProjectedBy();
    }
}