package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.entity.Place;

import java.util.List;
import java.util.Set;

public interface PlaceService {
    Place findPlaceByQuery(String query);
    Place findPlaceById(String id);
    List<Place> findAlongRoute(Place origin, Place destination, Integer travelTime, Set<Category> categories);
    Place save(Place place);
    List<Place> saveAll(List<Place> places);
}
