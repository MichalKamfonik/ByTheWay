package pl.kamfonik.bytheway.service.interfaces;

import pl.kamfonik.bytheway.dto.Route;
import pl.kamfonik.bytheway.entity.Place;

import java.util.List;
import java.util.Optional;

public interface RouteService {
    Optional<Route> getRoute(Place origin, Place destination);

    Optional<Route> getRoute(List<Place> places);
}
