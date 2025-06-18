package com.sukream.sukream.domains.user.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserConfiguration is a Querydsl query type for UserConfiguration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserConfiguration extends EntityPathBase<UserConfiguration> {

    private static final long serialVersionUID = 1623096447L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserConfiguration userConfiguration = new QUserConfiguration("userConfiguration");

    public final com.sukream.sukream.commons.jpa.QBaseTimeEntity _super = new com.sukream.sukream.commons.jpa.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath emailSubscription = createBoolean("emailSubscription");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath marketingConsent = createBoolean("marketingConsent");

    public final BooleanPath privacyPolicy = createBoolean("privacyPolicy");

    public final BooleanPath pushNotification = createBoolean("pushNotification");

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUsers users;

    public QUserConfiguration(String variable) {
        this(UserConfiguration.class, forVariable(variable), INITS);
    }

    public QUserConfiguration(Path<? extends UserConfiguration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserConfiguration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserConfiguration(PathMetadata metadata, PathInits inits) {
        this(UserConfiguration.class, metadata, inits);
    }

    public QUserConfiguration(Class<? extends UserConfiguration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new QUsers(forProperty("users")) : null;
    }

}

