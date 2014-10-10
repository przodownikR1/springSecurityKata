package pl.java.scalatech.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.assembler.RoleAssembler;
import pl.java.scalatech.assembler.RoleResource;
import pl.java.scalatech.domain.Role;
import pl.java.scalatech.rest.ApiError;
import pl.java.scalatech.service.role.RoleService;

@RestController
@RequestMapping(value = RoleContoller.API, produces = { MediaType.APPLICATION_JSON_VALUE })
@Slf4j
public class RoleContoller extends CrudController<Role, String> {

    private final RoleAssembler roleAssembler;
    private final RoleService roleService;
    protected static final String API = "/api/role";
    @Autowired
    public RoleContoller(RoleService roleService) {
        super(roleService);
        this.roleService = roleService;
        this.roleAssembler = new RoleAssembler(this.getClass(), RoleResource.class);
    }

    @Override
    protected ResponseEntity<?> getRightResponseEntity(Role t) {
        if (t == null) { return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND.value(), messageSource.getMessage("not exists", new Object[] { t },
                locale)), HttpStatus.NOT_FOUND); }
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @Override
    protected ResourceAssemblerSupport<Role, ?> getRas(Role role) {
        return this.roleAssembler;
    }
}
