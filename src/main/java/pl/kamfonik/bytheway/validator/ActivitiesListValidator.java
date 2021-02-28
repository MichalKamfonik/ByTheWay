package pl.kamfonik.bytheway.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kamfonik.bytheway.entity.Activity;
import pl.kamfonik.bytheway.service.CategoryService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Slf4j
public class ActivitiesListValidator implements ConstraintValidator<ActivitiesList, List<Activity>> {

    @Override
    public void initialize(ActivitiesList constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Activity> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        int i = 0;
        for (Activity activity : value) {
            if(!activity.getNumber().equals(i) || activity.getDuration()<0){
                return false;
            }
            i++;
        }
        log.debug("ActivitiesList validation for {}", value);
        return true;
    }
}