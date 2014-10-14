package pl.java.scalatech.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.data.neo4j.annotation.NodeEntity;


@NodeEntity
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends CommonNeo4jEntity implements Comparable<Role> {

    private @NonNull @NotNull final String name;

    @Override
    public int compareTo(Role o) {
        return this.name.compareTo(o.name);
    }
}
