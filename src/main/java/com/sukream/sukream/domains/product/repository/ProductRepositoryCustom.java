package com.sukream.sukream.domains.product.repository;

import com.sukream.sukream.domains.product.dto.ProductResponse;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductResponse> findByCategoryAndSort(String category, String sort);
}
