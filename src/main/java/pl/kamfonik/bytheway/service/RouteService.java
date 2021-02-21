package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.dto.Route;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.entity.Trip;

public interface RouteService {
    Route getRoute(Place origin, Place destination);
    Route getRoute(Trip trip);
}
