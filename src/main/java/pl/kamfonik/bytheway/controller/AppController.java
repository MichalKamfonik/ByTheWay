package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.entity.Activity;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/app")
public class AppController {
    private final PlanService planService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final RouteService routeService;
    private final PlaceService placeService;
    private final TripService tripService;

    @GetMapping("/initialize")
    public String initializeCategories(@AuthenticationPrincipal CurrentUser user) {
        if (user.getUser().getRoles().stream()
                .anyMatch(r -> "ROLE_ADMIN".equals(r.getName()))) {
            categoryService.initialize();
        }
        return "redirect:/app";
    }

    @GetMapping
    public String dashboard(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        model.addAttribute("plans", planService.findUserPlans(user));
        model.addAttribute("categories", user.getFavoriteCategories());
        return "app/dashboard";
    }

    @GetMapping("/categories")
    public String manageCategoriesForm(Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("user", user.getUser());
        return "app/categories";
    }

    @PostMapping("/categories")
    public String manageCategories(@ModelAttribute User user, @AuthenticationPrincipal CurrentUser currentUser) {
        User currentUserUser = currentUser.getUser();
        currentUserUser.setFavoriteCategories(user.getFavoriteCategories());
        userService.updateUser(currentUserUser);
        return "redirect:/app";
    }

    @GetMapping("/add-trip1")
    public String addTripForm1(Model model) {
        model.addAttribute("trip", new Trip());
        return "app/addTrip1";
    }

    @PostMapping("/add-trip1")
    public String addTrip1(@ModelAttribute Trip trip, Model model, @AuthenticationPrincipal CurrentUser user,
                           @RequestParam String place1, @RequestParam String place2) {

        Place origin = placeService.findPlaceByQuery(place1);
        Place destination = placeService.findPlaceByQuery(place2);
        int travelTime = routeService.calculateRouteTime(origin, destination) / 60; // in minutes

        trip = tripService.initialize(trip, origin, destination, travelTime);

        model.addAttribute("trip", trip);

        List<Place> alongRoute = placeService.findAlongRoute(origin, destination, travelTime*60,
                user.getUser().getFavoriteCategories());

        model.addAttribute("alongRoute", alongRoute);

        return "/app/addTrip2";
    }

    @PostMapping("/add-trip2")
    public String addTrip2(@ModelAttribute Trip trip, Model model, @AuthenticationPrincipal CurrentUser user) {
        Trip forUpdate = tripService.findTripById(trip.getId());
        forUpdate.getActivities().addAll(1,trip.getActivities());
        List<Activity> activities = forUpdate.getActivities();

        for(int i=1; i<activities.size(); i++){
            Activity current = activities.get(i);
            Activity previous = activities.get(i-1);
            current.setArrival(previous.getDeparture()
                    .plusMinutes(routeService.calculateRouteTime(previous.getPlace(), current.getPlace())/60));
            current.setDeparture(current.getArrival()); // no duration yet
        }

        model.addAttribute("trip",forUpdate);
        return "/app/addTrip3";
    }

    @PostMapping("/add-trip3")
    @ResponseBody
    public Trip addTrip3(@ModelAttribute Trip trip, Model model, @AuthenticationPrincipal CurrentUser user) {
        Trip forUpdate = tripService.findTripById(trip.getId());

        log.debug("forUpdate = {}", forUpdate);
        log.debug("trip = {}", trip);

        forUpdate.getActivities().addAll(1,
                trip.getActivities().stream()
                        .filter(a->!"__ORIGIN__".equals(a.getDescription()) && !"__DESTINATION__".equals(a.getDescription()))
                        .collect(Collectors.toList()));

//        model.addAttribute("trip",forUpdate);
//        return "/app/addTrip3";
        return forUpdate;
    }
}
