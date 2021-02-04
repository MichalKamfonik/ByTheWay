package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.entity.Place;

import java.util.List;

public interface PlaceService {
    List<Place> findPlaces(String query);
}
