package pl.kamfonik.bytheway.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PlaceIDValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlaceID {
    String message() default "{pl.kamfonik.bytheway.validator.PlaceID.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}