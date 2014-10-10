package pl.java.scalatech.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
@PropertySource("classpath:mail.properties")

public class MailConfig {
    @Autowired
    private Environment env;
    
    @Bean
    	public JavaMailSenderImpl javaMailSenderImpl() {
    		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
    		mailSenderImpl.setHost(env.getProperty("smtp.host"));
    		mailSenderImpl.setPort(env.getProperty("smtp.port", Integer.class));
    		mailSenderImpl.setProtocol(env.getProperty("smtp.protocol"));
    		mailSenderImpl.setUsername(env.getProperty("smtp.username"));
    		mailSenderImpl.setPassword(env.getProperty("smtp.password"));
    
    		Properties javaMailProps = new Properties();
    		javaMailProps.put("mail.smtp.auth", true);
    		javaMailProps.put("mail.smtp.starttls.enable", true);
    
    		mailSenderImpl.setJavaMailProperties(javaMailProps);
    
    		return mailSenderImpl;
    	}
}

