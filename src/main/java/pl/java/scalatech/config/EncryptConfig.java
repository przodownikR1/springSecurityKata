package pl.java.scalatech.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configurable
public class EncryptConfig {
    @Autowired
    private Environment environment;

    @Bean
    public StringEncryptor stringEncryptor() {
        PBEStringEncryptor encryption = new StandardPBEStringEncryptor();
       // String passwd = environment.getRequiredProperty("encryption.password") == null ? environment.getRequiredProperty("encryption.password") : "slawek";
        String passwd = "slawek";
        encryption.setPassword(passwd);
        return encryption;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
