package pl.kamfonik.bytheway.converter;

import org.springframework.core.convert.converter.Converter;
import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.service.CategoryService;


public class CategoryConverter implements Converter<String, Category> {
    private final CategoryService categoryService;

    public CategoryConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Category convert(String source) {
        return categoryService.findById(Long.parseLong(source));
    }
}
