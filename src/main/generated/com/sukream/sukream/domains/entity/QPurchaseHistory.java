package com.sukream.sukream.domains.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPurchaseHistory is a Querydsl query type for PurchaseHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseHistory extends EntityPathBase<PurchaseHistory> {

    private static final long serialVersionUID = 1509067104L;

    public static final QPurchaseHistory purchaseHistory = new QPurchaseHistory("purchaseHistory");

    public final NumberPath<User> buyer = createNumber("buyer", User.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Product> product = createNumber("product", Product.class);

    public final DateTimePath<java.time.LocalDateTime> purchasedTime = createDateTime("purchasedTime", java.time.LocalDateTime.class);

    public QPurchaseHistory(String variable) {
        super(PurchaseHistory.class, forVariable(variable));
    }

    public QPurchaseHistory(Path<? extends PurchaseHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPurchaseHistory(PathMetadata metadata) {
        super(PurchaseHistory.class, metadata);
    }

}

