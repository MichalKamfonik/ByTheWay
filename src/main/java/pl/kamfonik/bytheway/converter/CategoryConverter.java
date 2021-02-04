package pl.kamfonik.bytheway.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.service.CategoryService;


public class CategoryConverter implements Converter<String, Category> {
    @Autowired
    private CategoryService categoryService;

    @Override
    public Category convert(String source) {
        return categoryService.findById(Long.parseLong(source));
    }
}
