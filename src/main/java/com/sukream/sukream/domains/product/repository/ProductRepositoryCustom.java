package com.sukream.sukream.domains.product.repository;

import com.sukream.sukream.domains.product.dto.ProductResponse;
import com.sukream.sukream.domains.product.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductResponse> findByCategoryAndSort(String category, String sort);
    List<Product> findAllForDeadlineCheck(String category);
}
