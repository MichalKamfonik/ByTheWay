package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.dto.*;
import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.repository.PlaceRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaceServiceTomTomDB implements PlaceService {

    private final CategoryService categoryService;
    private final ByTheWayProperties byTheWayProperties;
    private final PlaceRepository placeRepository;

    private static final Integer MAX_DETOUR_PROCENT = 25;

    private static final String TOMTOM_SEARCH_POI_API_URL =
            "https://api.tomtom.com/search/2/poiSearch/__QUERY__.json" +
                    "?typeahead=true" +
                    "&limit=1" +
                    "&countrySet=PL&key=";
    private static final String TOMTOM_GET_POI_BY_ID_API_URL =
            "https://api.tomtom.com/search/2/place.json" +
                    "?entityId=__ID__&key=";
    private static final String TOMTOM_SEARCH_ALONG_ROUTE_API_URL =
            "https://api.tomtom.com/search/2/searchAlongRoute/%20.json" +
                    "?maxDetourTime=__MAX_DETOUR_TIME__" +
                    "&typeahead=true" +
                    "&spreadingMode=auto" +
                    "&limit=20" +
                    "&categorySet=__CATEGORY_SET__" +
                    "&detourOffset=true" +
                    "&sortBy=detourOffset" +
                    "&key=";

    @Override
    public Place findPlaceById(String id) {

        Optional<Place> byId = placeRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }

        String url = TOMTOM_GET_POI_BY_ID_API_URL.replace("__ID__", id)
                + byTheWayProperties.getSearchPOI().getApikey();

        ResponseEntity<SearchResultTableDto> forEntity = tryGetForEntity(url);

        return Objects.requireNonNull(forEntity.getBody()).getResults().stream()
                .map(this::searchResultsToPlaces)
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public Place findPlaceByQuery(String query) {
        String url = TOMTOM_SEARCH_POI_API_URL.replace("__QUERY__", query)
                + byTheWayProperties.getSearchPOI().getApikey();


        ResponseEntity<SearchResultTableDto> forEntity = tryGetForEntity(url);

        return Objects.requireNonNull(forEntity.getBody()).getResults().stream()
                .map(SearchResultDto::getId)
                .map(this::findPlaceById)
                .collect(Collectors.toList())
                .get(0);
    }

    private ResponseEntity<SearchResultTableDto> tryGetForEntity(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SearchResultTableDto> forEntity;
        try {
            forEntity = restTemplate.getForEntity(url, SearchResultTableDto.class);
        } catch (HttpClientErrorException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                log.error("Sleep exception"); // log and ignore
            }
            forEntity = restTemplate.getForEntity(url, SearchResultTableDto.class);
        }
        return forEntity;
    }

    @Override
    public List<Place> findAlongRoute(Place origin, Place destination, Integer travelTime, Set<Category> categories) {
        String url = getUrl(travelTime, categories);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject routeToSend = getJsonObject(origin, destination);

        HttpEntity<String> request = new HttpEntity<>(routeToSend.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SearchAlongRouteResultTableDto> forEntity;
        try {
            forEntity = restTemplate.postForEntity(url,request, SearchAlongRouteResultTableDto.class);
        } catch (HttpClientErrorException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                log.error("Sleep exception"); // log and ignore
            }
            forEntity = restTemplate.postForEntity(url,request,SearchAlongRouteResultTableDto.class);
        }

        return Objects.requireNonNull(forEntity.getBody()).getResults().stream()
                .map(this::searchResultsToPlaces)
                .collect(Collectors.toList());
    }

    @Override
    public Place save(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public List<Place> saveAll(List<Place> places) {
        return placeRepository.saveAll(places);
    }

    @Override
    public void clear() {
        placeRepository.deleteAll();
    }

    private String getUrl(Integer travelTime, Set<Category> categories) {
        Integer maxDetourInt = travelTime * MAX_DETOUR_PROCENT / 100;
        if (maxDetourInt > 3600) {
            maxDetourInt = 3600;
        }
        String maxDetourTime = String.valueOf(maxDetourInt);
        String categorySet = categories.stream()
                .map(Category::getId)
                .map(Object::toString)
                .collect(Collectors.joining(","));

        return TOMTOM_SEARCH_ALONG_ROUTE_API_URL
                .replace("__MAX_DETOUR_TIME__", maxDetourTime)
                .replace("__CATEGORY_SET__", categorySet)
                + byTheWayProperties.getSearchPOI().getApikey();
    }

    private JSONObject getJsonObject(Place origin, Place destination) {
        JSONObject routeToSend = new JSONObject();
        JSONObject points = new JSONObject();
        JSONObject originPoint = new JSONObject();
        JSONObject destinationPoint = new JSONObject();

        originPoint.put("lon", origin.getLon());
        originPoint.put("lat", origin.getLat());
        destinationPoint.put("lon", destination.getLon());
        destinationPoint.put("lat", destination.getLat());

        points.put("points", List.of(originPoint, destinationPoint));
        routeToSend.put("route", points);
        return routeToSend;
    }

    private Place searchResultsToPlaces(SearchResultDto result) {
        Place place = new Place();
        place.setId(result.getId());
        place.setName(result.getPoi().getName());
        place.setAddress(result.getAddress().getFreeformAddress());
        place.setLat(result.getPosition().getLat());
        place.setLon(result.getPosition().getLon());
        place.setCategories(categoryService
                .findCategoriesWithIds(
                        result.getPoi()
                                .getCategorySet()
                                .stream()
                                .map(PoiCategoryDto::getId)
                                .map(id -> id.toString().substring(0, 4))
                                .map(Long::parseLong)
                                .collect(Collectors.toList())
                )
        );
        log.debug("result class = {}",result.getClass());
        if(result instanceof SearchAlongRouteResultDto){
            place.setDetourOffset(((SearchAlongRouteResultDto) result).getDetourOffset());
        } else {
            place.setDetourOffset(0);
        }
        return place;
    }
}
