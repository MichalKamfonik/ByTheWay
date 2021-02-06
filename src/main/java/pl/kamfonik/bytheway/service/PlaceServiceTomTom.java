package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.dto.PoiCategoryDto;
import pl.kamfonik.bytheway.dto.SearchResultTableDto;
import pl.kamfonik.bytheway.entity.Place;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceTomTom implements PlaceService {

    private final CategoryService categoryService;
    private final ByTheWayProperties byTheWayProperties;
    private static final String TOMTOM_SEARCH_POI_API_URL =
            "https://api.tomtom.com/search/2/poiSearch/__QUERY__.json?typeahead=true&countrySet=PL&key=";

    @Override
    public List<Place> findPlaces(String query) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SearchResultTableDto> forEntity = restTemplate.getForEntity(
                TOMTOM_SEARCH_POI_API_URL.replace("__QUERY__",
                        URLEncoder.encode(query, StandardCharsets.UTF_8))
                        + byTheWayProperties.getSearchPOI().getApikey(),
                SearchResultTableDto.class
        );

        return forEntity.getBody().getResults().stream()
                .map(result -> {
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
                                            .map(id->id.toString().substring(0,4))
                                            .map(Long::parseLong)
                                            .collect(Collectors.toList())
                            ));
                    return place;
                })
                .collect(Collectors.toList());
    }
}
