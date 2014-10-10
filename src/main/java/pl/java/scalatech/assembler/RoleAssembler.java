package pl.java.scalatech.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.java.scalatech.domain.Role;

public class RoleAssembler extends ResourceAssemblerSupport<Role, RoleResource> {

    public RoleAssembler(Class<?> controllerClass, Class<RoleResource> resourceType) {
        super(controllerClass, resourceType);

    }

    @Override
    public RoleResource toResource(Role entity) {
        RoleResource rr = new RoleResource();
        rr.setDesc(entity.getDesc());
        rr.setRoleId(entity.getId());
        return rr;
    }

}
