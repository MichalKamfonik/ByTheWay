package pl.kamfonik.bytheway.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CategoryIDValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryID {
    String message() default "{pl.kamfonik.bytheway.validator.Category.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}