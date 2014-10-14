package pl.java.scalatech.test;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import pl.java.scalatech.config.AppConfig;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { AppConfig.class })
@Slf4j
@Ignore
// TODO repair test
@WebAppConfiguration
public class BootstrapTest {

    
    @Test
    public void shouldWork(){
        log.info("ok");
    }
}

