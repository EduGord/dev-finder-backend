package com.edug.devfinder.validators.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String PATTERN = "^[\\w-\\.]{3,}@([\\w-]{3,}\\.)+[\\w-]{2,4}$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        return input != null && Pattern.compile(PATTERN)
                .matcher(input)
                .matches();
    }
}
