package pl.kamfonik.bytheway.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.entity.Place;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class PlacesListEntity2DtoConverter implements Entity2DtoConverter<List<Place>, List<PlaceDto>> {
    private final Entity2DtoConverter<Place, PlaceDto> placeEntity2DtoConverter;

    @Override
    public List<PlaceDto> convert(List<Place> entity) {

        return IntStream
                .range(0, entity.size())
                .mapToObj(i -> {
                    PlaceDto placeDto = placeEntity2DtoConverter.convert(entity.get(i));
                    placeDto.setNumber(i);
                    return placeDto;
                })
                .collect(Collectors.toList());
    }
}
