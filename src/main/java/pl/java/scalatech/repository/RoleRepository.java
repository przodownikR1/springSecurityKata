package pl.java.scalatech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.java.scalatech.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String>{

}
