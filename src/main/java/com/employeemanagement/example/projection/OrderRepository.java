package com.employeemanagement.example.projection;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<OrderProjection> findAllProjectedBy();
}
