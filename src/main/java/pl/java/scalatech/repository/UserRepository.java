package pl.java.scalatech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.java.scalatech.domain.User;


public interface UserRepository extends MongoRepository<User, String>{
    
    User findUserByLogin(String login);
    User findUserByLoginOrEmail(String token);

}
