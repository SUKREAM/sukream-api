package com.sukream.sukream.domains.product.repository;

import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.user.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOwner(Users user);
}
