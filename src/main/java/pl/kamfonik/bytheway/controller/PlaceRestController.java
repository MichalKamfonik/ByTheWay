package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.converter.Entity2DtoConverter;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.entity.CurrentUser;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceRestController {
    private final PlaceService placeService;
    private final Entity2DtoConverter<Place, PlaceDto> placeEntity2DtoConverter;
    private final Entity2DtoConverter<List<Place>, List<PlaceDto>> placeListEntity2DtoConverter;

    @PostMapping("/find")
    public ResponseEntity<PlaceDto> findPlace(@RequestParam String query) {
        Optional<Place> placeByQuery = placeService.findPlaceByQuery(query);
        return placeByQuery
                .map(placeEntity2DtoConverter::convert)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/find-along-route/{travelTime}")
    public List<PlaceDto> findAlongRoute(
            @RequestBody List<PlaceDto> places,
            @PathVariable Integer travelTime,
            @AuthenticationPrincipal CurrentUser currentUser) {

        List<Place> alongRoute = placeService.findAlongRoute(
                placeService.findPlaceById(places.get(0).getId()).orElseThrow(),
                placeService.findPlaceById(places.get(1).getId()).orElseThrow(),
                travelTime,
                currentUser.getUser().getFavoriteCategories());

        return placeListEntity2DtoConverter.convert(alongRoute);
    }
}
