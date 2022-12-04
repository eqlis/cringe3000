package com.cringe.cringe3000.annotation;

import com.cringe.cringe3000.validation.EmailExistenceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.cringe.cringe3000.util.Constants.EXISTS;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailExistenceValidator.class)
public @interface EmailExists {

  String message() default EXISTS;
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
