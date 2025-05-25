package com.sukream.sukream.domains.product.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sukream.sukream.domains.product.dto.ProductResponse;
import com.sukream.sukream.domains.product.entity.Product;
import com.sukream.sukream.domains.product.entity.QProduct;
import com.sukream.sukream.domains.bidder.entity.QBidder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductResponse> findByCategoryAndSort(String category, String sort) {
        return findProductsWithBidCountAndSort(category, sort);
    }

    private List<ProductResponse> findProductsWithBidCountAndSort(String category, String sort) {
        QProduct product = QProduct.product;
        QBidder bidder = QBidder.bidder;

        var bidCountExpr = bidder.count();

        var query = queryFactory.select(product, bidCountExpr)
                .from(product)
                .leftJoin(bidder).on(bidder.product.id.eq(product.id));

        if (category != null && !category.isEmpty()) {
            query.where(product.category.eq(category));
        }

        query.groupBy(
                product.id,
                product.auctionNum,
                product.bidUnit,
                product.category,
                product.chatLink,
                product.createdAt,
                product.deadline,
                product.description,
                product.image,
                product.maxPrice,
                product.minPrice,
                product.owner.id,
                product.status,
                product.title,
                product.updatedAt
        );

        if ("popular".equalsIgnoreCase(sort)) {
            query.orderBy(bidCountExpr.desc(), product.createdAt.desc());
        } else {
            query.orderBy(product.createdAt.desc());
        }

        List<Tuple> result = query.fetch();

        return result.stream()
                .map(tuple -> {
                    Long bidCountObj = tuple.get(bidCountExpr);
                    int bidCount = bidCountObj == null ? 0 : bidCountObj.intValue();
                    return ProductResponse.fromEntityAndBidCount(tuple.get(product), bidCount);
                })
                .toList();
    }

    @Override
    public List<Product> findAllForDeadlineCheck(String category) {
        QProduct product = QProduct.product;

        var query = queryFactory.selectFrom(product);

        if (category != null && !category.isEmpty()) {
            query.where(product.category.eq(category));
        }
        return query.fetch();
    }
}
