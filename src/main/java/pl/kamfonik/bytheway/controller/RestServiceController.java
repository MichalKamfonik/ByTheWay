package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.dto.RouteObjectForMapping;
import pl.kamfonik.bytheway.entity.Activity;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.PlaceService;
import pl.kamfonik.bytheway.service.RouteService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/rest")
public class RestServiceController {
    private final PlaceService placeService;
    private final RouteService routeService;

    @PostMapping("/find-place")
    public Place findPlace(@RequestParam String query) {
        return placeService.findPlaceByQuery(query);
    }

    @PostMapping("/calculate-route")
    public Integer calculateRoute(@RequestBody List<Place> places) {
        return routeService.getRoute(places.get(0), places.get(1)).getRouteTime();
    }

    @PostMapping("/find-along-route/{travelTime}")
    public List<Place> findAlongRoute(
            @RequestBody List<Place> places,
            @PathVariable Integer travelTime,
            @AuthenticationPrincipal CurrentUser currentUser) {
        return placeService.findAlongRoute(
                places.get(0),
                places.get(1),
                travelTime,
                currentUser.getUser().getFavoriteCategories());
    }

    @PostMapping("/find-route")
    public RouteObjectForMapping getRouteObjectForMapping(@RequestBody List<Activity> activities) {
        Trip trip = new Trip();
        trip.setActivities(activities);
        return routeService.getRoute(trip).getRouteObjectForMapping();
    }
}
