package com.employeemanagement.example.projection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;

    public DataInitializer(ProductRepository productRepository, OrderRepository orderRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
		/*
		 * Category electronics = new Category(); electronics.setName("Electronics");
		 * categoryRepository.save(electronics); Product phone = Product.builder()
		 * .price( 699.99) .category(electronics) .name("Smartphone") .build(); Product
		 * laptop = Product.builder() .price( 999.99) .category(electronics)
		 * .name("Laptop") .build(); productRepository.save(phone);
		 * productRepository.save(laptop);
		 * 
		 * HashSet<Product> products = new HashSet<>(); products.add(phone);
		 * products.add(laptop);
		 * 
		 * Order order = Order.builder() .products(products) .build();
		 */
      //  orderRepository.save(order);
    }
}
