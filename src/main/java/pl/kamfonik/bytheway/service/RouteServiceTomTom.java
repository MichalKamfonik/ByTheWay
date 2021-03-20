package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.dto.Route;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.dto.route.RouteDto;
import pl.kamfonik.bytheway.dto.route.RoutesTableDto;
import pl.kamfonik.bytheway.service.interfaces.RouteService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteServiceTomTom implements RouteService {

    private final ByTheWayProperties byTheWayProperties;
    private static final String TOMTOM_ROUTING_API_URL =
            "https://api.tomtom.com/routing/1/calculateRoute/__LOCATIONS__/json?key=";

    @Override
    public Route getRoute(PlaceDto origin, PlaceDto destination) {
        String locations =
                origin.getLat() + "," + origin.getLon() + ":" +
                        destination.getLat() + "," + destination.getLon();

        String url = TOMTOM_ROUTING_API_URL.replace("__LOCATIONS__", locations)
                + byTheWayProperties.getRouting().getApikey();

        return getRoute(url);
    }

    @Override
    public Route getRoute(List<PlaceDto> places) {
        String locations = places.stream()
                .map(p->p.getLat() + "," + p.getLon())
                .collect(Collectors.joining(":"));

        String url = TOMTOM_ROUTING_API_URL.replace("__LOCATIONS__", locations)
                + byTheWayProperties.getRouting().getApikey();

        return getRoute(url);
    }

    private RouteDto getRoute(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RoutesTableDto> forEntity;
        try {
            forEntity = restTemplate.getForEntity(
                    url,
                    RoutesTableDto.class
            );
        } catch (HttpClientErrorException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                log.error("Sleep exception"); // log and ignore
            }
            forEntity = restTemplate.getForEntity(url,RoutesTableDto.class);
        }
        return Objects.requireNonNull(forEntity.getBody()).getRoutes().get(0);
    }
}
