package com.cringe.cringe3000.validation;

import com.cringe.cringe3000.annotation.EmailExists;
import com.cringe.cringe3000.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class EmailExistenceValidator implements ConstraintValidator<EmailExists, String> {

  private final UserService userService;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return userService.findByEmail(email).isEmpty();
  }
}
