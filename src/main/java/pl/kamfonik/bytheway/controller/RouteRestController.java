package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamfonik.bytheway.dto.RouteObjectForMapping;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.service.interfaces.RouteService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/route")
public class RouteRestController {
    private final RouteService routeService;

    @PostMapping("/calculate")
    public Integer calculateRoute(@RequestBody List<PlaceDto> places) {
        return routeService.getRoute(places.get(0), places.get(1)).getRouteTime();
    }

    @PostMapping("/find")
    public RouteObjectForMapping getRouteObjectForMapping(@RequestBody List<PlaceDto> places) {
        return routeService.getRoute(places).getRouteObjectForMapping();
    }
}
