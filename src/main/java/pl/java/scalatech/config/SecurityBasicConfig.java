package pl.java.scalatech.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import pl.java.scalatech.annotation.SecurityComponent;

@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(EncryptConfig.class)
@Slf4j
@ComponentScan(basePackages = { "pl.java.scalatech.security" }, useDefaultFilters = false, includeFilters = { @Filter(Service.class), @Filter(SecurityComponent.class) })
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter {
  
    @Configuration
    @Order(1)                                                        
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationSuccessHandler authSuccessHandler;
        @Autowired
        private LogoutSuccessHandler logoutSuccessHander;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets/**").antMatchers("/css/**").antMatchers("/js/**").antMatchers("/images/**").antMatchers("/favicon.ico");
        }
        
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
               .authorizeRequests().antMatchers("/welcome", "/api/ping", "/signup", "/about","/register").permitAll()
              .antMatchers("/api/admin/**").hasRole("ADMIN")
              .antMatchers("/api/appContext").hasRole("ADMIN")
              .antMatchers("/api/user/**").hasRole("USER")
              .antMatchers("/api/business**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_BUSINESS')")
              .anyRequest().authenticated();
                    

       
            http
            .formLogin()
            .loginPage("/welcome").failureUrl("/welcome?error=true").successHandler(authSuccessHandler).defaultSuccessUrl("/welcome")
            .permitAll()
            .and()
            .logout().logoutSuccessUrl("/welcome").invalidateHttpSession(true).logoutSuccessHandler(logoutSuccessHander).deleteCookies("JSESSIONID")
             .permitAll();
            
            
        }
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

            log.info("+++++                         inMemoryAuth");
            auth.inMemoryAuthentication()
    .withUser("user").password("slawek").roles("USER").and().withUser("business").password("slawek").roles("BUSINESS").and()
                    .withUser("admin").password("slawek").roles("USER", "ADMIN");

        }
    }
        

    @Configuration         
    @Order(2)  
    public static class APISecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests().antMatchers("/assets/**","/css/**","/js/**","/images/**","welcome","/api/ping", "/signup", "/about").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll();
        }
        
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

            log.info("+++++                         inMemoryAuth2");
            auth.inMemoryAuthentication()
    .withUser("user").password("slawek").roles("USER").and().withUser("business").password("slawek").roles("BUSINESS").and()
                    .withUser("admin").password("slawek").roles("USER", "ADMIN");

        }
    }

    
}
