package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamfonik.bytheway.dto.RouteObjectForMapping;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;
import pl.kamfonik.bytheway.service.interfaces.RouteService;

import java.util.List;
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
        Place origin = placeService.findPlaceById(places.get(0).getId());
        Place destination = placeService.findPlaceById(places.get(1).getId());

        return routeService.getRoute(origin, destination).getRouteTime();
    }

    @PostMapping("/find")
    public RouteObjectForMapping getRouteObjectForMapping(@RequestBody List<PlaceDto> placeDtos) {
        List<Place> places = placeDtos.stream()
                .map(PlaceDto::getId)
                .map(placeService::findPlaceById)
                .collect(Collectors.toList());
        return routeService.getRoute(places).getRouteObjectForMapping();
    }
}
