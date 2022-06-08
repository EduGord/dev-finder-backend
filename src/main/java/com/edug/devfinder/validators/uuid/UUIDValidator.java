package com.edug.devfinder.validators.uuid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDValidator implements ConstraintValidator<ValidUUID, UUID> {
    private static final String PATTERN = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";

    @Override
    public void initialize(ValidUUID constraintAnnotation) {
    }

    @Override
    public boolean isValid(UUID input, ConstraintValidatorContext context) {
        return input != null && Pattern.compile(PATTERN)
                .matcher(input.toString())
                .matches();
    }
}
