package pl.kamfonik.bytheway.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.service.PlaceService;

public class PlaceConverter  implements Converter <String, Place>{
    @Autowired
    private PlaceService placeService;

    @Override
    public Place convert(String s) {
        return placeService.findPlaceById(s);
    }
}