package com.cringe.cringe3000.util;

public class Constants {
  public static final String REQUIRED = "required";
  public static final String MAX = "max";
  public static final String EXISTS = "exists";
  public static final String PATTERN = "pattern";
  public static final String FUTURE = "future";
  public static final String NEGATIVE = "negative";

  public static final String EMAIL_VALIDATION = "^(?=.{1,64}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$";
  public static final String USERNAME_VALIDATION = "^(?=.*[a-zA-Z])(?=.+[a-zA-Z0-9@._-]).{6,}$";
  public static final String PASSWORD_VALIDATION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";

  public static final String REGISTRATION_CONFIRMATION = "Registration Confirmation";
  public static final String CHANGE_PASSWORD = "Change Password";
  public static final String CONFIRMATION_INSTRUCTION = "To activate your account follow the link:";
  public static final String FORGOT_PASSWORD_INSTRUCTION = "To change password follow the link:";
  public static final String CONFIRMATION_LINK = "http://localhost:8080/activate/";
  public static final String CHANGE_PASSWORD_LINK = "http://localhost:8080/change-password/";

  public static final String DATE_FORMAT = "dd-MM-yyyy";

}
