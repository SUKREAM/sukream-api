package com.sukream.sukream.domains.product.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sukream.sukream.domains.product.dto.ProductResponse;
import com.sukream.sukream.domains.product.entity.QProduct;
import com.sukream.sukream.domains.bidder.entity.QBidder;
import com.sukream.sukream.domains.user.domain.entity.QUsers;
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
        QUsers owner = product.owner;

        var bidCountExpr = bidder.count();

        var query = queryFactory.select(
                        product.id,
                        owner.id,
                        owner.name,
                        product.title,
                        product.description,
                        product.minPrice,
                        product.maxPrice,
                        product.category,
                        product.bidUnit,
                        product.createdAt,
                        product.updatedAt,
                        product.deadline,
                        product.chatLink,
                        product.status.stringValue(),
                        product.auctionNum,
                        bidCountExpr
                )
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
                product.maxPrice,
                product.minPrice,
                owner.id,
                owner.name,
                product.status,
                product.title,
                product.updatedAt
        );

        if ("popular".equalsIgnoreCase(sort)) {
            query.orderBy(bidCountExpr.desc(), product.createdAt.desc());
        } else {
            query.orderBy(product.createdAt.desc());
        }

        List<Tuple> results = query.fetch();

        return results.stream()
                .map(tuple -> {
                    Long id = tuple.get(product.id);
                    Long ownerId = tuple.get(owner.id);
                    String ownerName = tuple.get(owner.name);
                    String title = tuple.get(product.title);
                    String description = tuple.get(product.description);
                    Integer minPriceObj = tuple.get(product.minPrice);
                    int minPrice = minPriceObj != null ? minPriceObj : 0;
                    Integer maxPriceObj = tuple.get(product.maxPrice);
                    int maxPrice = maxPriceObj != null ? maxPriceObj : 0;
                    String categoryVal = tuple.get(product.category);
                    Integer bidUnitObj = tuple.get(product.bidUnit);
                    int bidUnit = bidUnitObj != null ? bidUnitObj : 0;
                    java.time.LocalDateTime createdAt = tuple.get(product.createdAt);
                    java.time.LocalDateTime updatedAt = tuple.get(product.updatedAt);
                    java.time.LocalDateTime deadline = tuple.get(product.deadline);
                    String chatLink = tuple.get(product.chatLink);
                    String status = tuple.get(product.status.stringValue());
                    String auctionNum = tuple.get(product.auctionNum);
                    Long bidCountLong = tuple.get(bidCountExpr);
                    int bidCount = bidCountLong != null ? bidCountLong.intValue() : 0;

                    String image = null; // 목록에서는 이미지 null 처리

                    return ProductResponse.builder()
                            .id(id)
                            .sellerId(ownerId)
                            .sellerName(ownerName)
                            .title(title)
                            .description(description)
                            .minPrice(minPrice)
                            .maxPrice(maxPrice)
                            .category(categoryVal)
                            .bidUnit(bidUnit)
                            .createdAt(createdAt)
                            .updatedAt(updatedAt)
                            .deadline(deadline)
                            .chatLink(chatLink)
                            .status(status)
                            .auctionNum(auctionNum)
                            .image(image)
                            .bidCount(bidCount)
                            .build();
                })
                .toList();
    }

    @Override
    public List<com.sukream.sukream.domains.product.entity.Product> findAllForDeadlineCheck(String category) {
        QProduct product = QProduct.product;

        var query = queryFactory.selectFrom(product);

        if (category != null && !category.isEmpty()) {
            query.where(product.category.eq(category));
        }
        return query.fetch();
    }
}
