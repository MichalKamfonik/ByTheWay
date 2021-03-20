package pl.kamfonik.bytheway.service.interfaces;

import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.entity.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceService {
    Optional<Place> findPlaceByQuery(String query);

    Optional<Place> findPlaceById(String id);

    List<Place> findAlongRoute(Place origin, Place destination, Integer travelTime, List<Category> categories);

    Optional<Place> save(Place place);

    List<Place> saveAll(List<Place> places);

    void clear();
}
