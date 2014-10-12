package pl.java.scalatech;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import pl.java.scalatech.config.AppConfig;
import pl.java.scalatech.service.user.UserService;

@Configuration
@EnableAutoConfiguration
@EnableAsync
@Slf4j
public class SecurityKataApp extends SpringBootServletInitializer {
    private static final String DEV = "dev";

    @Autowired
    private UserService userService;

    @Bean
    InitializingBean populateData(final UserService userService) {
        return () -> {
            
        };
    }

    public static void main(String[] args) {
        // System.setProperty("spring.profiles.default", System.getProperty("spring.profiles.default", "prop"));
        SpringApplication.run(SecurityKataApp.class, args);

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        SpringApplicationBuilder app = application.profiles(addDefaultProfile()).sources(WebConfiguration.class);

        return app;
    }

    @Configuration
    @Import(AppConfig.class)
    @ComponentScan(excludeFilters = @Filter({ Service.class, Configuration.class }))
    static class WebConfiguration {

    }

    private String addDefaultProfile() {
        String profile = System.getProperty("spring.profiles.default");
        if (profile != null) {
            log.info("+++                                     Running with Spring profile(s) : {}", profile);
            return profile;
        }
        log.warn("+++                                    No Spring profile configured, running with default configuration");
        return DEV;
    }

}