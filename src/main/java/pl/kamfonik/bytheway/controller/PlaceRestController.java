package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.PlaceService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceRestController {
    private final PlaceService placeService;

    @PostMapping("/find")
    public Place findPlace(@RequestParam String query) {
        return placeService.findPlaceByQuery(query);
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
}
