package pl.java.scalatech.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.hdiv.config.annotation.EnableHdivWebSecurity;
import org.hdiv.config.annotation.ExclusionRegistry;
import org.hdiv.config.annotation.ValidationConfigurer;
import org.hdiv.config.annotation.configuration.HdivWebSecurityConfigurerAdapter;
import org.hdiv.filter.ValidatorFilter;
import org.hdiv.listener.InitListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;



@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableHdivWebSecurity
public class HdivAutoConfiguration  extends HdivWebSecurityConfigurerAdapter {
        @Override
        public void addExclusions(ExclusionRegistry registry) {

            registry.addUrlExclusions("/webjars/.*").method("GET");
            registry.addUrlExclusions(".*.js").method("GET");
            registry.addUrlExclusions(".*.css").method("GET");
            registry.addUrlExclusions(".*.png").method("GET");
            registry.addUrlExclusions(".*.jpg").method("GET");
            registry.addUrlExclusions(".*.ico").method("GET");

            registry.addUrlExclusions("/").method("GET");

            registry.addUrlExclusions("/health");
            registry.addUrlExclusions("/beans").method("GET");
            registry.addUrlExclusions("/trace").method("GET");
            registry.addUrlExclusions("/configprops").method("GET");
            registry.addUrlExclusions("/info").method("GET");
            registry.addUrlExclusions("/dump").method("GET");
            registry.addUrlExclusions("/autoconfig").method("GET");
            registry.addUrlExclusions("/metrics", "/metrics/.*").method("GET");
            registry.addUrlExclusions("/env", "/env/.*").method("GET");
            registry.addUrlExclusions("/mappings").method("GET");

            registry.addParamExclusions("_csrf");
        }

        @Override
        public void configureEditableValidation(ValidationConfigurer validationConfigurer) {

            validationConfigurer.addValidation(".*");
        }
    
   
    @Bean
    public ServletContextInitializer validatorFilter() {
        ServletContextInitializer initializer = new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {

                servletContext.addListener(new InitListener());

                FilterRegistration.Dynamic registration = servletContext.addFilter("validatorFilter", new ValidatorFilter());
                EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST);

                registration.addMappingForUrlPatterns(dispatcherTypes, false, "/*");
            }
        };
        return initializer;
    }
}
