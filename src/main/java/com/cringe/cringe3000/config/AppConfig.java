package com.cringe.cringe3000.config;

import com.cringe.cringe3000.repository.UserRepository;
import com.cringe.cringe3000.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Properties;

@Configuration
public class AppConfig {

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new UserDetailsServiceImpl(userRepository);
  }

  @Bean
  public JavaMailSender javaMailSender(EmailConfig config) {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setProtocol(config.getProtocol());
    javaMailSender.setHost(config.getHost());
    javaMailSender.setPort(config.getPort());
    javaMailSender.setUsername(config.getUsername());
    javaMailSender.setPassword(config.getPassword());

    Properties props = javaMailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", config.getProtocol());
    props.put("mail.smtp.auth", config.isAuth());
    props.put("mail.smtp.starttls.enable", config.isSmtpStarttlsEnable());

    return javaMailSender;
  }

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

}
