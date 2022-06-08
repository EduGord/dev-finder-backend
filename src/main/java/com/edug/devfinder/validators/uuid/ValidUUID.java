package com.edug.devfinder.validators.uuid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element uuid must be an universally unique identifier (UUID).
 * <p/>
 * <p/>
 * {@code null} elements are considered valid.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = UUIDValidator.class)
@Retention(RUNTIME)
public @interface ValidUUID {

    String message() default "invalid uuid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
