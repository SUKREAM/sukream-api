package com.sukream.sukream.domains.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.entity.QProduct;
import com.sukream.sukream.domains.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductResponse> findByCategoryAndSort(String category, String sort) {
        QProduct product = QProduct.product;

        var query = queryFactory.selectFrom(product);

        if (category != null && !category.isEmpty()) {
            query.where(product.category.eq(category));
        }

        if ("latest".equalsIgnoreCase(sort)) {
            query.orderBy(product.createdAt.desc());
        } else if ("popular".equalsIgnoreCase(sort)) {
            query.orderBy(product.bidCount.desc());
        }

        List<Product> products = query.fetch();

        return products.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
