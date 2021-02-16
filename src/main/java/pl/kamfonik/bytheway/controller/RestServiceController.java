package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.entity.Place;
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
        log.debug("findPlace incoming request = {}", query);
        return placeService.findPlaceByQuery(query);
    }

    @PostMapping("/calculate-route")
    public Integer calculateRoute(@RequestBody List<Place> places) {
        log.debug("calculateRoute incoming request = {}", places);
        return routeService.calculateRouteTime(places.get(0), places.get(1));
    }

    @PostMapping("/find-along-route/{travelTime}")
    public List<Place> findAlongRoute(
            @RequestBody List<Place> places,
            @PathVariable Integer travelTime,
            @AuthenticationPrincipal CurrentUser currentUser) {
        log.debug("calculateRoute incoming request = {}", places);
        log.debug("travel time: {}", travelTime);
        log.debug("current user categories: {}", currentUser.getUser().getFavoriteCategories());
        List<Place> list = placeService.findAlongRoute(
                places.get(0),
                places.get(1),
                travelTime,
                currentUser.getUser().getFavoriteCategories());
        log.debug("List: {}", list);
        return list;
    }
}
