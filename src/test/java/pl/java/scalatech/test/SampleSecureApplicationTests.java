package pl.java.scalatech.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.config.MongoRepositoryConfig;
import pl.java.scalatech.config.SecurityBasicConfig;
import pl.java.scalatech.config.ServiceConfig;
import pl.java.scalatech.service.SampleService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { SecurityBasicConfig.class,MongoRepositoryConfig.class,MongoDBConfig.class ,ServiceConfig.class })
public class SampleSecureApplicationTests {
    @Autowired
    private SampleService service;
    @Autowired
    private ApplicationContext context;
    private Authentication authentication;
    @Before
    public void init() {
        AuthenticationManager authenticationManager = this.context.getBean(AuthenticationManager.class);
        this.authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("user", "slawek"));
    }

    @After
    public void close() {
        SecurityContextHolder.clearContext();
    }

    @Test(expected = AuthenticationException.class)
    public void secure() throws Exception {
        assertEquals(this.service.secure(), "Hello Security");
    }

    @Test
    public void authenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(this.authentication);
        assertEquals(this.service.secure(), "Hello Security");
    }

    @Test
    public void preauth() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(this.authentication);
        assertEquals(this.service.authorized(), "Hello World");
    }

    @Test
    public void denied() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(this.authentication);
        assertEquals(this.service.denied(), "Goodbye World");
    }

}