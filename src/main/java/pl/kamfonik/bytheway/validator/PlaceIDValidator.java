package pl.kamfonik.bytheway.validator;

import lombok.RequiredArgsConstructor;
import pl.kamfonik.bytheway.service.PlaceService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class PlaceIDValidator implements ConstraintValidator<PlaceID, String> {

    private final PlaceService placeService;

    @Override
    public void initialize(PlaceID constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return placeService.findPlaceById(value) != null;
    }
}