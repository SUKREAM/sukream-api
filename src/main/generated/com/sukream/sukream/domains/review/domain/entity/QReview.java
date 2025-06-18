package com.sukream.sukream.domains.review.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 395114199L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.sukream.sukream.domains.product.entity.QProduct product;

    public final EnumPath<QualityAssesment> qualityAssessment = createEnum("qualityAssessment", QualityAssesment.class);

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final com.sukream.sukream.domains.user.domain.entity.QUsers writer;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.sukream.sukream.domains.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.writer = inits.isInitialized("writer") ? new com.sukream.sukream.domains.user.domain.entity.QUsers(forProperty("writer")) : null;
    }

}

