package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamfonik.bytheway.dto.Route;
import pl.kamfonik.bytheway.dto.RouteObjectForMapping;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;
import pl.kamfonik.bytheway.service.interfaces.RouteService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/route")
public class RouteRestController {
    private final RouteService routeService;
    private final PlaceService placeService;

    @PostMapping("/calculate")
    public Integer calculateRoute(@RequestBody List<PlaceDto> places) {
        Optional<Place> origin = placeService.findPlaceById(places.get(0).getId());
        Optional<Place> destination = placeService.findPlaceById(places.get(1).getId());
        if(origin.isEmpty() || destination.isEmpty()){
            return -1;
        }
        return routeService.getRoute(origin.get(), destination.get()).map(Route::getRouteTime).orElseThrow();
    }

    @PostMapping("/find")
    public RouteObjectForMapping getRouteObjectForMapping(@RequestBody List<PlaceDto> placeDtos) {
        List<Place> places = placeDtos.stream()
                .map(PlaceDto::getId)
                .map(placeService::findPlaceById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return routeService.getRoute(places).map(Route::getRouteObjectForMapping).orElseThrow();
    }
}
