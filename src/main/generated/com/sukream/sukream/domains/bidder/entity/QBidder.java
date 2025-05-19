package com.sukream.sukream.domains.bidder.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBidder is a Querydsl query type for Bidder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBidder extends EntityPathBase<Bidder> {

    private static final long serialVersionUID = -1126036675L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBidder bidder = new QBidder("bidder");

    public final com.sukream.sukream.commons.jpa.QBaseTimeEntity _super = new com.sukream.sukream.commons.jpa.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAwarded = createBoolean("isAwarded");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.sukream.sukream.domains.product.entity.QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sukream.sukream.domains.user.domain.entity.QUser user;

    public QBidder(String variable) {
        this(Bidder.class, forVariable(variable), INITS);
    }

    public QBidder(Path<? extends Bidder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBidder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBidder(PathMetadata metadata, PathInits inits) {
        this(Bidder.class, metadata, inits);
    }

    public QBidder(Class<? extends Bidder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.sukream.sukream.domains.product.entity.QProduct(forProperty("product")) : null;
        this.user = inits.isInitialized("user") ? new com.sukream.sukream.domains.user.domain.entity.QUser(forProperty("user")) : null;
    }

}

