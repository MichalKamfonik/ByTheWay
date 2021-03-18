package pl.kamfonik.bytheway.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kamfonik.bytheway.dto.rest.CategoryDto;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.entity.Place;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlaceEntity2DtoConverter implements Entity2DtoConverter<Place, PlaceDto> {

    private final Entity2DtoConverter<Category, CategoryDto> categoryEntity2DtoConverter;
    @Override
    public PlaceDto convert(Place entity) {
        return new PlaceDto(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getLon(),
                entity.getLat(),
                entity.getCategories()
                        .stream()
                        .map(categoryEntity2DtoConverter::convert)
                        .collect(Collectors.toSet()),
                null);
    }
}
