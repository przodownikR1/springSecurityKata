package pl.java.scalatech.assembler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class RoleResource extends ResourceSupport {
    private String roleId;

    private String desc;
}
