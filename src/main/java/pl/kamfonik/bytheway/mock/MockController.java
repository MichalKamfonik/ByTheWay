package pl.kamfonik.bytheway.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.PlaceService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/mock")
public class MockController {

    private final ByTheWayProperties byTheWayProperties;
    private final PlaceService placeService;

    public MockController(ByTheWayProperties byTheWayProperties, PlaceService placeService) {
        this.byTheWayProperties = byTheWayProperties;
        this.placeService = placeService;
    }

    @GetMapping
    public String home(){
        log.debug(byTheWayProperties.getCategory().getApikey());
        return "home";
    }
    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "Admin - admins only";
    }
    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal CurrentUser customUser){
        User entityUser = customUser.getUser();
        return "Hello " + entityUser.toString();
    }
    @GetMapping("/trip/{query}")
    @ResponseBody
    public List<String> tripQuery(@PathVariable String query){
        placeService.findPlaces(query).forEach(place-> log.debug(place.toString()));
        return placeService.findPlaces(query).stream().map(Place::toString).collect(Collectors.toList());
    }
}
