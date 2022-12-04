package com.cringe.cringe3000.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EmailConfig {

  @Value("${spring.mail.host}")
  private String host;
  @Value("${spring.mail.port}")
  private int port;
  @Value("${spring.mail.username}")
  private String username;
  @Value("${spring.mail.password}")
  private String password;
  @Value("${spring.mail.properties.mail.smtp.auth}")
  private boolean auth;
  @Value("${spring.mail.properties.mail.transport.protocol}")
  private String protocol;
  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private boolean smtpStarttlsEnable;

}
