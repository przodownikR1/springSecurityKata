package pl.java.scalatech.test.encrypt;

import static org.fest.assertions.Assertions.assertThat;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.EncryptConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EncryptConfig.class })
@ActiveProfiles(value="prod")
@Slf4j
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
    public void shouldEncodeAndDecodePassword(){
        String hash = passwordEncoder.encode("test");
        log.info("+++    {}",hash);
        assertThat(passwordEncoder.matches("test", hash)).isTrue();
    }
}
