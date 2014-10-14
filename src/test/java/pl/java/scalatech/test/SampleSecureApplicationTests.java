package pl.java.scalatech.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.config.MongoRepositoryConfig;
import pl.java.scalatech.config.SecurityBasicConfig;
import pl.java.scalatech.config.ServiceConfig;
import pl.java.scalatech.service.MyService;

@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
//TODO repair test
@SpringApplicationConfiguration(classes = { SecurityBasicConfig.class, MongoRepositoryConfig.class, MongoDBConfig.class, ServiceConfig.class })
public class SampleSecureApplicationTests {
    @Autowired
    private MyService service;
    @Autowired
    private ApplicationContext context;
    private Authentication authentication;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Before
    public void init() {
        AuthenticationManager authenticationManager = this.context.getBean(AuthenticationManager.class);
        this.authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("user", passwordEncoder.encode("test")));
    }

    /*
     * @Test
     * public void test() {
     * Role r_user = new Role("user", "user");
     * User user = User.builder().login("przodownik").password(passwordEncoder.encode("test")).activated(true).email("przodownik@tlen.pl").fistName("slawek")
     * .lastName("borowiec").logged(false).roles(newArrayList(r_user)).build();
     * UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
     * Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), userDetails.getAuthorities());
     * SecurityContextHolder.getContext().setAuthentication(authentication);
     * }
     */
    @After
    public void close() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void shouldSecure() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(this.authentication);
        assertEquals(this.service.secure(), MyService.RESULT);

    }

    @Test
    public void shouldAuthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(this.authentication);
        assertEquals(this.service.secure(), MyService.RESULT);
    }

    @Test
    public void shouldPreauth() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(this.authentication);
        assertEquals(this.service.authorized(), MyService.RESULT);
    }

    @Test(expected = AccessDeniedException.class)
    public void shouldDenied() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(this.authentication);
        assertEquals(this.service.denied(), MyService.RESULT);
    }

}