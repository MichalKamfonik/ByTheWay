package pl.kamfonik.bytheway.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.service.interfaces.CategoryService;

@Component
@RequiredArgsConstructor
public class CategoryConverter implements Converter<String, Category> {

    private final CategoryService categoryService;

    @Override
    public Category convert(@NonNull String s) {
        return categoryService.findById(Long.parseLong(s));
    }
}
