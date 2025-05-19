package com.sukream.sukream.domains.product.repository;

import com.sukream.sukream.domains.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
