package com.sukream.sukream.domains.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalesHistory is a Querydsl query type for SalesHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalesHistory extends EntityPathBase<SalesHistory> {

    private static final long serialVersionUID = 198347771L;

    public static final QSalesHistory salesHistory = new QSalesHistory("salesHistory");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Product> product = createNumber("product", Product.class);

    public final NumberPath<User> seller = createNumber("seller", User.class);

    public QSalesHistory(String variable) {
        super(SalesHistory.class, forVariable(variable));
    }

    public QSalesHistory(Path<? extends SalesHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalesHistory(PathMetadata metadata) {
        super(SalesHistory.class, metadata);
    }

}

