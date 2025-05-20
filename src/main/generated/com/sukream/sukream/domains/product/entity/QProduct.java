package com.sukream.sukream.domains.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1642928677L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.sukream.sukream.commons.jpa.QBaseTimeEntity _super = new com.sukream.sukream.commons.jpa.QBaseTimeEntity(this);

    public final StringPath auctionNum = createString("auctionNum");

    public final NumberPath<Integer> bidAccount = createNumber("bidAccount", Integer.class);

    public final NumberPath<Integer> bidUnit = createNumber("bidUnit", Integer.class);

    public final StringPath category = createString("category");

    public final StringPath chatLink = createString("chatLink");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deadline = createDateTime("deadline", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath image = createString("image");

    public final NumberPath<Integer> maxPrice = createNumber("maxPrice", Integer.class);

    public final NumberPath<Integer> minPrice = createNumber("minPrice", Integer.class);

    public final com.sukream.sukream.domains.user.domain.entity.QUsers owner;

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath status = createString("status");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new com.sukream.sukream.domains.user.domain.entity.QUsers(forProperty("owner")) : null;
    }

}

