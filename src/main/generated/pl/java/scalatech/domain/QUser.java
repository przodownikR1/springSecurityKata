package pl.java.scalatech.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 945277225L;

    public static final QUser user = new QUser("user");

    public final QPKEntity _super = new QPKEntity(this);

    public final BooleanPath accountNonExpired = createBoolean("accountNonExpired");

    public final BooleanPath accountNonLocked = createBoolean("accountNonLocked");

    public final BooleanPath activated = createBoolean("activated");

    public final StringPath activatedCode = createString("activatedCode");

    public final NumberPath<Integer> attemptLoginCount = createNumber("attemptLoginCount", Integer.class);

    public final DateTimePath<java.util.Date> attemptLoginDate = createDateTime("attemptLoginDate", java.util.Date.class);

    public final CollectionPath<org.springframework.security.core.GrantedAuthority, SimplePath<org.springframework.security.core.GrantedAuthority>> authorities = this.<org.springframework.security.core.GrantedAuthority, SimplePath<org.springframework.security.core.GrantedAuthority>>createCollection("authorities", org.springframework.security.core.GrantedAuthority.class, SimplePath.class, PathInits.DIRECT2);

    public final BooleanPath credentialsNonExpired = createBoolean("credentialsNonExpired");

    //inherited
    public final DatePath<java.time.LocalDate> dateAdded = _super.dateAdded;

    //inherited
    public final DatePath<java.time.LocalDate> dateModification = _super.dateModification;

    //inherited
    public final BooleanPath disabled = _super.disabled;

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath fistName = createString("fistName");

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> lastIncorrectLogin = createDateTime("lastIncorrectLogin", java.util.Date.class);

    public final DateTimePath<java.util.Date> lastLoginAt = createDateTime("lastLoginAt", java.util.Date.class);

    public final StringPath lastName = createString("lastName");

    public final BooleanPath logged = createBoolean("logged");

    public final StringPath login = createString("login");

    public final StringPath password = createString("password");

    public final ListPath<Role, QRole> roles = this.<Role, QRole>createList("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata<?> metadata) {
        super(User.class, metadata);
    }

}

