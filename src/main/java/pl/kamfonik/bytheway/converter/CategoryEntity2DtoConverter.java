package pl.kamfonik.bytheway.converter;

import org.springframework.stereotype.Component;
import pl.kamfonik.bytheway.dto.rest.CategoryDto;
import pl.kamfonik.bytheway.entity.Category;

@Component
public class CategoryEntity2DtoConverter implements Entity2DtoConverter<Category, CategoryDto> {
    @Override
    public CategoryDto convert(Category entity) {
        return new CategoryDto(entity.getId(),entity.getName());
    }
}
