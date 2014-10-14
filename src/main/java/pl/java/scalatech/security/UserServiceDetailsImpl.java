package pl.java.scalatech.security;

import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.collect.Lists;

import pl.java.scalatech.annotation.SecurityComponent;
import pl.java.scalatech.domain.Role;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.RoleRepository;
import pl.java.scalatech.repository.UserRepository;

@Slf4j
@SecurityComponent
@DependsOn("stringEncryptor")
public class UserServiceDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    List<User> users = Lists.newArrayList();

    @PostConstruct
    public void init() {
        log.info("+++                                      role : {} , user : {}      userDetailsService postConstruct" + roleRepository.count(),
                userRepository.count());
        log.info("{}", passwordEncoder.encode("test"));

        if (roleRepository.count() == 2) {

            Role r_user = new Role("user", "user");
            roleRepository.save(r_user);
            Role a_user = new Role("admin", "admin");
            roleRepository.save(a_user);
            Role b_user = new Role("business", "buisness");
            roleRepository.save(b_user);
            log.info("+++                               {}", roleRepository.findOne("user"));

        }
        log.info("+++                               {}", roleRepository.findOne("user"));

        if (userRepository.count() == 0) {
            User one = User.builder().login("przodownik").password(passwordEncoder.encode("test")).activated(true).email("przodownik@tlen.pl")
                    .fistName("slawek").lastName("borowiec").logged(false).roles(Lists.newArrayList(roleRepository.findOne("user"))).build();

            userRepository.save(one);

            User two = User.builder().login("admin").password(passwordEncoder.encode("test")).activated(true).email("admin@tlen.pl").fistName("admin")
                    .lastName("borowiec").logged(false).roles(Lists.newArrayList(roleRepository.findOne("admin"))).build();
            userRepository.save(two);
            User three = User.builder().login("buisness").password(passwordEncoder.encode("test")).activated(true).email("business@tlen.pl")
                    .fistName("buisness").lastName("borowiec").logged(false).roles(Lists.newArrayList(roleRepository.findOne("business"))).build();
            userRepository.save(three);
            User four = User.builder().login("bak").password(passwordEncoder.encode("test")).activated(true).email("bak@tlen.pl").fistName("kalinka")
                    .lastName("borowiec").logged(false)
                    .roles(Lists.newArrayList(roleRepository.findOne("user"), roleRepository.findOne("admin"), roleRepository.findOne("business"))).build();
            User fourLoaded = userRepository.save(four);
            fourLoaded.getRoles().add(roleRepository.findOne("user"));
            userRepository.save(fourLoaded);

        }
        log.info("+++                               {}  --> roles : {}    ", userRepository.findUserByLogin("przodownik"),userRepository.findUserByLogin("przodownik").getAuthorities());
        // log.info("+++                               {}", userRepository.findUserByLoginOrEmail("przodownik"));
    }

    @Autowired
    private StringEncryptor strongEncryptor;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        User user = findUserByLoginOrMail(username);
        if (user == null) { throw new UsernameNotFoundException("login.not.exists"); }

        if (user.getAttemptLoginCount() >= 3) {
            throw new UsernameNotFoundException("attempt.3x.login.error");

        } else if (!user.isAccountNonLocked() || !user.isEnabled()) { throw new UsernameNotFoundException("user.block"); }

        return user;
    }

    private User findUserByLoginOrMail(String loginOrMail) {
        User user = null;
        try {
            // TODO dao problem
            user = userRepository.findUserByLogin(loginOrMail);
        } catch (Exception e) {
            log.error("Login or mail not exists : {}  , exception {}", loginOrMail, e);
        }

        return user;
    }

}
