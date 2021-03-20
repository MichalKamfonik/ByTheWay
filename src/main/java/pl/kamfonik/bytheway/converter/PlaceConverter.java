package pl.kamfonik.bytheway.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;

@Component
@RequiredArgsConstructor
public class PlaceConverter  implements Converter <String, Place>{

    private final PlaceService placeService;

    @Override
    public Place convert(@NonNull String s) {
        return placeService.findPlaceById(s).orElseThrow();
    }
}