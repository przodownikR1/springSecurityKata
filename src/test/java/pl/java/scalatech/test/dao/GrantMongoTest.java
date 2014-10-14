package pl.java.scalatech.test.dao;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.EncryptConfig;
import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.config.MongoRepositoryConfig;
import pl.java.scalatech.config.ServiceConfig;
import pl.java.scalatech.domain.Role;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.RoleRepository;
import pl.java.scalatech.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MongoRepositoryConfig.class, MongoDBConfig.class, ServiceConfig.class, EncryptConfig.class })
@Slf4j
public class GrantMongoTest {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldSaveWork() {
        List<User> users = newArrayList();
        roleRepository.deleteAll();
        userRepository.deleteAll();
        log.info("+++               role : {} , user : {}      userDetailsService postConstruct" + roleRepository.count(), userRepository.count());

        Role r_user = new Role("user", "user");
        roleRepository.save(r_user);
        Role a_user = new Role("admin", "admin");
        roleRepository.save(a_user);
        Role b_user = new Role("business", "buisness");
        roleRepository.save(b_user);

        users.add(User.builder().login("przodownik").password(passwordEncoder.encode("test")).activated(true).email("przodownik@tlen.pl").fistName("slawek").lastName("borowiec")
                .logged(false).roles(newArrayList(roleRepository.findOne("user"))).build());
        users.add(User.builder().login("admin").password(passwordEncoder.encode("test")).activated(true).email("admin@tlen.pl").fistName("admin").lastName("borowiec").logged(false)
                .roles(newArrayList(roleRepository.findOne("admin"))).build());
        users.add(User.builder().login("buisness").password(passwordEncoder.encode("test")).activated(true).email("business@tlen.pl").fistName("buisness").lastName("borowiec")
                .logged(false).roles(newArrayList(roleRepository.findOne("business"))).build());
        users.add(User.builder().login("bak").password(passwordEncoder.encode("test")).activated(true).email("bak@tlen.pl").fistName("kalinka").lastName("borowiec").logged(false)
                .roles(newArrayList(roleRepository.findOne("user"), roleRepository.findOne("admin"), roleRepository.findOne("business"))).build());

        users.forEach(u -> userRepository.save(u));

        Assertions.assertThat(userRepository.findUserByLogin("przodownik").getRoles()).contains(r_user);
        users.forEach(u -> log.info("{}", userRepository.findUserByLogin(u.getLogin()).getRoles()));
    
    }

}
