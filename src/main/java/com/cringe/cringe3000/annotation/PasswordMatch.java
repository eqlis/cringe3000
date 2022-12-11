package com.cringe.cringe3000.annotation;

import com.cringe.cringe3000.validation.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.cringe.cringe3000.util.Constants.DOES_NOT_MATCH;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {

  String message() default DOES_NOT_MATCH;
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
