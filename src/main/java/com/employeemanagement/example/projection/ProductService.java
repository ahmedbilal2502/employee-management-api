package com.employeemanagement.example.projection;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductProjection> getAllProducts() {
		return productRepository.findAllProjectedBy();
	}
}