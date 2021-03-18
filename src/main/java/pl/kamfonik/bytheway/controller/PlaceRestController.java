package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.converter.Entity2DtoConverter;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
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
    private final Entity2DtoConverter<Place, PlaceDto> placeEntity2DtoConverter;
    private final Entity2DtoConverter<List<Place>, List<PlaceDto>> placeListEntity2DtoConverter;

    @PostMapping("/find")
    public PlaceDto findPlace(@RequestParam String query) {
        Place placeByQuery = placeService.findPlaceByQuery(query);
        return placeEntity2DtoConverter.convert(placeByQuery);
    }

    @PostMapping("/find-along-route/{travelTime}")
    public List<PlaceDto> findAlongRoute(
            @RequestBody List<PlaceDto> places,
            @PathVariable Integer travelTime,
            @AuthenticationPrincipal CurrentUser currentUser) {

        List<Place> alongRoute = placeService.findAlongRoute(
                placeService.findPlaceById(places.get(0).getId()),
                placeService.findPlaceById(places.get(1).getId()),
                travelTime,
                currentUser.getUser().getFavoriteCategories());

        return placeListEntity2DtoConverter.convert(alongRoute);
    }
}
