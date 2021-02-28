package pl.kamfonik.bytheway.validator;

import lombok.RequiredArgsConstructor;
import pl.kamfonik.bytheway.service.CategoryService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class CategoryIDValidator implements ConstraintValidator<CategoryID, Long> {

    private final CategoryService categoryService;

    @Override
    public void initialize(CategoryID constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return categoryService.findById(value) != null;
    }
}