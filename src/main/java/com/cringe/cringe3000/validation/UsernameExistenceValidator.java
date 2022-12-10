package com.cringe.cringe3000.validation;

import com.cringe.cringe3000.annotation.UsernameExists;
import com.cringe.cringe3000.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UsernameExistenceValidator implements ConstraintValidator<UsernameExists, String> {

  private final UserService userService;

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    return userService.findByUsername(username).isEmpty();
  }
}
