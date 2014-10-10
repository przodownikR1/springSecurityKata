package pl.java.scalatech.service.role.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.java.scalatech.domain.Role;
import pl.java.scalatech.repository.RoleRepository;
import pl.java.scalatech.service.common.impl.PaginationAbstactService;
import pl.java.scalatech.service.role.RoleService;
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl extends PaginationAbstactService<Role, String> implements RoleService{
    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

}
