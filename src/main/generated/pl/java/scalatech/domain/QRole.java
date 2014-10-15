package pl.java.scalatech.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = 945184212L;

    public static final QRole role = new QRole("role");

    public final QPKEntity _super = new QPKEntity(this);

    //inherited
    public final DatePath<java.time.LocalDate> dateAdded = _super.dateAdded;

    //inherited
    public final DatePath<java.time.LocalDate> dateModification = _super.dateModification;

    public final StringPath desc = createString("desc");

    //inherited
    public final BooleanPath disabled = _super.disabled;

    public final StringPath id = createString("id");

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<? extends Role> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRole(PathMetadata<?> metadata) {
        super(Role.class, metadata);
    }

}

