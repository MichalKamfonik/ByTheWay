package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.dto.RoutesTableDto;
import pl.kamfonik.bytheway.entity.Place;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteServiceTomTom implements RouteService {

    private final ByTheWayProperties byTheWayProperties;
    private static final String TOMTOM_ROUTING_API_URL =
            "https://api.tomtom.com/routing/1/calculateRoute/__LOCATIONS__/json?key=";

    @Override
    public Integer calculateRouteTime(Place origin, Place destination) {
        String locations =
                origin.getLat() + "," + origin.getLon() + ":" +
                        destination.getLat() + "," + destination.getLon();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RoutesTableDto> forEntity = restTemplate.getForEntity(
                TOMTOM_ROUTING_API_URL.replace("__LOCATIONS__", locations)
                        + byTheWayProperties.getRouting().getApikey(),
                RoutesTableDto.class
        );
        Integer travelTimeInSeconds = forEntity.getBody().getRoutes().get(0).getSummary().getTravelTimeInSeconds();
        log.debug("travelTimeInSeconds: {}",travelTimeInSeconds);
        return travelTimeInSeconds;
    }
}
