package pl.java.scalatech.security;

import lombok.extern.slf4j.Slf4j;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.java.scalatech.annotation.SecurityComponent;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;


@Slf4j
@SecurityComponent
@DependsOn("stringEncryptor")
public class UserServiceDetailsImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringEncryptor strongEncryptor;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        User user = findUserByLoginOrMail(username);
        if (user == null) {
            throw new UsernameNotFoundException("login.not.exists");
        }
        
        if (user.getAttemptLoginCount() >= 3) {
            throw new UsernameNotFoundException("attempt.3x.login.error");

        } else if (!user.isAccountNonLocked() || !user.isEnabled()) {
            throw new UsernameNotFoundException("user.block");
        }

        return user;
    }

    private User findUserByLoginOrMail(String loginOrMail) {
        User user = null;
        try {
            user = userRepository.findUserByLoginOrEmail(loginOrMail);
        } catch (Exception e) {
            log.error("Login or mail not exists : {}  , exception {}", loginOrMail, e);
        }

        return user;
    }

}
