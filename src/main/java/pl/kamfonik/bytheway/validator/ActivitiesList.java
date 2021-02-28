package pl.kamfonik.bytheway.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ActivitiesListValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivitiesList {
    String message() default "{pl.kamfonik.bytheway.validator.ActivitiesList.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}