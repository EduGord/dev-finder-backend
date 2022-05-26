package com.edug.devfinder.validators.password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,20}$";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        return input != null && Pattern.compile(PATTERN)
                .matcher(input)
                .matches();
    }
}
