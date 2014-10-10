package pl.java.scalatech.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.service.common.impl.PaginationAbstactService;
import pl.java.scalatech.service.user.UserService;

/**
 * @author Sławomir Borowiec
 *         Module name : bootSetting
 *         Creating time : 17 wrz 2014 09:49:17
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends PaginationAbstactService<User, String> implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

}
