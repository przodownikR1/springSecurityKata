package pl.java.scalatech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
@Configuration
@Import({ MongoDBConfig.class, MongoRepositoryConfig.class, PropConfig.class, WebConfig.class, RestConfig.class, ServiceConfig.class, WebSecurityConfig.class })
public class AppConfig {

    
}