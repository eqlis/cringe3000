package com.cringe.cringe3000.validation;

import com.cringe.cringe3000.annotation.EmailExists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class EmailExistenceValidator implements ConstraintValidator<EmailExists, String> {

  // TODO: implement validation
  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return true;
  }
}
