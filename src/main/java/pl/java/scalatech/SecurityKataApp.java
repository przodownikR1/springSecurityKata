package pl.java.scalatech;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.java.scalatech.config.AppConfig;
import pl.java.scalatech.domain.Role;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.RoleRepository;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.service.user.UserService;

@Configuration
@EnableAutoConfiguration
@EnableAsync
@Slf4j
public class SecurityKataApp extends SpringBootServletInitializer {
    private static final String DEV = "dev";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    InitializingBean populateData(final UserService userService) {
        return () -> {
            populateDB();
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

    private void populateDB() {
        List<User> users = newArrayList();
        roleRepository.deleteAll();
        userRepository.deleteAll();

        Role r_user = new Role("user", "user");
        roleRepository.save(r_user);
        Role a_user = new Role("admin", "admin");
        roleRepository.save(a_user);
        Role b_user = new Role("business", "buisness");
        roleRepository.save(b_user);

        users.add(User.builder().login("przodownik").password(passwordEncoder.encode("test")).activated(true).email("przodownik@tlen.pl").fistName("slawek")
                .lastName("borowiec").logged(false).roles(newArrayList(roleRepository.findOne("user"))).build());
        users.add(User.builder().login("admin").password(passwordEncoder.encode("test")).activated(true).email("admin@tlen.pl").fistName("admin")
                .lastName("borowiec").logged(false).roles(newArrayList(roleRepository.findOne("admin"))).build());
        users.add(User.builder().login("buisness").password(passwordEncoder.encode("test")).activated(true).email("business@tlen.pl").fistName("buisness")
                .lastName("borowiec").logged(false).roles(newArrayList(roleRepository.findOne("business"))).build());
        users.add(User.builder().login("bak").password(passwordEncoder.encode("test")).activated(true).email("bak@tlen.pl").fistName("kalinka")
                .lastName("borowiec").logged(false)
                .roles(newArrayList(roleRepository.findOne("user"), roleRepository.findOne("admin"), roleRepository.findOne("business"))).build());

        users.forEach(u -> userRepository.save(u));

        log.info("+++        {}  --> roles : {}    ", userRepository.findUserByLogin("przodownik"), userRepository.findUserByLogin("przodownik")
                .getAuthorities());
        // log.info("+++                               {}", userRepository.findUserByLoginOrEmail("przodownik"));
    }
}