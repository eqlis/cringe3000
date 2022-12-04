package com.cringe.cringe3000.service.impl;

import com.cringe.cringe3000.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  @Override
  public void sendMail(String to, String subject, String text) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(to);
    email.setSubject(subject);
    email.setText(text);
    mailSender.send(email);
  }

}
