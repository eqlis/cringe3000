package com.cringe.cringe3000.validation;

import com.cringe.cringe3000.annotation.PasswordMatch;
import com.cringe.cringe3000.model.dto.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, ChangePasswordRequest> {

  @Override
  public boolean isValid(ChangePasswordRequest changePasswordRequest, ConstraintValidatorContext constraintValidatorContext) {
    String password = changePasswordRequest.getNewPassword();
    String repeatPassword = changePasswordRequest.getRepeatPassword();
    return password.equals(repeatPassword);
  }
}
