package com.edug.devfinder.validators.enums;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NameOfEnumValidator implements ConstraintValidator<NameOfEnum, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(NameOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        return acceptedValues.contains(value.toString());
    }
}
