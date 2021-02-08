package pl.kamfonik.bytheway.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.kamfonik.bytheway.entity.Activity;
import pl.kamfonik.bytheway.service.PlaceService;

public class ActivityConverter implements Converter<String, Activity> {
    @Autowired
    private PlaceService placeService;
    @Override
    public Activity convert(String s) {
        Activity activity = new Activity();
        activity.setPlace(placeService.findPlaceById(s));
        return activity;
    }
}
