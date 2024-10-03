package com.employeemanagement.example.projection;

import java.util.Set;

public interface OrderProjection {
    Long getId();
    Set<ProductProjection> getProducts();
}
