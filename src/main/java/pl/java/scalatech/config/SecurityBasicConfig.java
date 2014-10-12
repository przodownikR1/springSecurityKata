package pl.java.scalatech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pl.java.scalatech.security.AuthSuccessHandlerImpl;

// @Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
// @EnableGlobalMethodSecurity(securedEnabled = true) //enable @Secured annotation
// @EnableGlobalMethodSecurity(jsr250Enabled = true) //enable jsr250 annotation
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// enable @PreAuthorize annotation
@Import(EncryptConfig.class)
@ComponentScan(basePackages = { "pl.java.scalatech.security" }, useDefaultFilters = false, includeFilters = { @Filter(Service.class), @Filter(Component.class) })
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter {

    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.err.println("inMemoryAuth");
        auth.inMemoryAuthentication()
.withUser("user").password("slawek").roles("USER").and().withUser("business").password("slawek").roles("BUSINESS").and()
                .withUser("admin").password("slawek").roles("USER", "ADMIN");

    }
    
   /* @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/assets/**","/css/**","/js/**","/images/**");
    }*/
    
    @Configuration
    @Order(1)                                                        
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationSuccessHandler authSuccessHandler;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
.authorizeRequests().antMatchers("/assets/**", "/css/**", "/js/**", "/images/**", "/welcome", "/api/ping", "/signup", "/about").permitAll()
                    .anyRequest().authenticated();

            http
            .formLogin()
                .loginPage("/welcome").failureUrl("/welcome?error=true").successHandler(authSuccessHandler)
.defaultSuccessUrl("/welcome")
                .permitAll()
                .and()
            .logout()
                .permitAll();
            
            /*
             * http.csrf().disable().authorizeRequests()
             * .antMatchers("/assets/**","/css/**","/js/**","/images/**","welcome","/api/ping", "/signup", "/about").permitAll()
             * .antMatchers("/api/admin/**").hasRole("ADMIN")
             * .antMatchers("/api/appContext").hasRole("ADMIN")
             * .antMatchers("/api/user/**").hasRole("USER")
             * .antMatchers("/api/business**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_BUSINESS')")
             * .anyRequest().authenticated();
             * http
             * .formLogin().failureUrl("/logout").successHandler(authSuccessHandler)
             * .defaultSuccessUrl("/welcome")
             * .loginPage("/welcome").permitAll().and()
             * .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/welcome").deleteCookies("JSESSIONID")
             * .permitAll();
             */
        }
        
    }
        

  /* @Configuration         
    @Order(2)  
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests().antMatchers("/assets/**","/css/**","/js/**","/images/**","welcome","/api/ping", "/signup", "/about").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll();
        }
    }
*/
    
}
