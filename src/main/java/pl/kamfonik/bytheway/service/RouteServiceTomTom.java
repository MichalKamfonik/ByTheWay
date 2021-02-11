package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.dto.RoutesTableDto;
import pl.kamfonik.bytheway.entity.Place;

import java.util.Objects;

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
        ResponseEntity<RoutesTableDto> forEntity;
        try {
            forEntity = restTemplate.getForEntity(
                    TOMTOM_ROUTING_API_URL.replace("__LOCATIONS__", locations)
                            + byTheWayProperties.getRouting().getApikey(),
                    RoutesTableDto.class
            );
        } catch (HttpClientErrorException e){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException exception){
                log.error("Sleep exception"); // log and ignore
            }
            forEntity = restTemplate.getForEntity(
                    TOMTOM_ROUTING_API_URL.replace("__LOCATIONS__", locations)
                            + byTheWayProperties.getRouting().getApikey(),
                    RoutesTableDto.class
            );
        }
        Integer travelTimeInSeconds = Objects.requireNonNull(forEntity.getBody())
                .getRoutes().get(0).getSummary().getTravelTimeInSeconds();
        log.debug("travelTimeInSeconds: {}",travelTimeInSeconds);
        return travelTimeInSeconds;
    }
}
