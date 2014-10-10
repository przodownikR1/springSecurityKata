package pl.java.scalatech.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Builder;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mysema.query.annotations.QueryEntity;

@Document
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@QueryEntity
public class Role extends PKEntity<String> {
   
    private static final long serialVersionUID = -3013930190003887457L;
    private String desc;
}