package pl.java.scalatech.test.encrypt;
import lombok.extern.slf4j.Slf4j;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { ProdPlaceholderConfig.class })
@ActiveProfiles(value="prod")
@Slf4j
public class EncryptTest {

    @Autowired
    private Environment env;
    
    @Value("${database.password}")
    private String password;
    
  /*  @Test
    public void shouldPropertiesRead(){
      log.info("+++   {}",env.getProperty("database.password"));  
    }*/
    
   @Test
    public void shouldEncryptAndDecrypt(){
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm("SHA-1");
        passwordEncryptor.setPlainDigest(true);
        String encryptedPassword = passwordEncryptor.encryptPassword("borowiec");
        
        if (passwordEncryptor.checkPassword("borowiec", encryptedPassword)) {
            log.info("ok ");
        } else {
            log.info("bad");
        }
    }
   @Test
   @Ignore
   public void shouldEncryptAndDecryptPBE(){
       PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
       encryptor.setPoolSize(4);          // This would be a good value for a 4-core system
       encryptor.setPassword("jasypt");
       encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
       String encryptedPassword = encryptor.encrypt("borowiec");
       
       
           log.info("ok {}",encryptor.decrypt(encryptedPassword));
       
   }
   
}
