package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.entity.Place;

import java.util.List;

public interface RouteService {
    Integer calculateRouteTime(Place origin, Place destination);
}