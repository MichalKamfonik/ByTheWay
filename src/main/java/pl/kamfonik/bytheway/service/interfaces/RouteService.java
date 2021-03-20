package pl.kamfonik.bytheway.service.interfaces;

import pl.kamfonik.bytheway.dto.Route;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.entity.Trip;

import java.util.List;

public interface RouteService {
    Route getRoute(PlaceDto origin, PlaceDto destination);
    Route getRoute(List<PlaceDto> places);
}
