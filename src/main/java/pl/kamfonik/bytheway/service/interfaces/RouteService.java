package pl.kamfonik.bytheway.service.interfaces;

import pl.kamfonik.bytheway.dto.Route;
import pl.kamfonik.bytheway.entity.Place;

import java.util.List;

public interface RouteService {
    Route getRoute(Place origin, Place destination);
    Route getRoute(List<Place> places);
}
