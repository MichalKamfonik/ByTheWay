package pl.kamfonik.bytheway.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kamfonik.bytheway.service.TripService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@RequiredArgsConstructor
public class TripIDValidator implements ConstraintValidator<TripID, Long> {

    private final TripService tripService;

    @Override
    public void initialize(TripID constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return tripService.findTripById(value) != null;
    }
}