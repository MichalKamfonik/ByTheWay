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
    public String addTrip1(@ModelAttribute Trip trip,
                           @AuthenticationPrincipal CurrentUser user,
                           @RequestParam String place1,
                           @RequestParam String place2,
                           Model model) {

        Place origin = placeService.findPlaceByQuery(place1);
        Place destination = placeService.findPlaceByQuery(place2);
        int travelTime = routeService.calculateRouteTime(origin, destination);

        trip = tripService.initialize(trip, origin, destination, travelTime);

        model.addAttribute("trip", trip);

        List<Place> alongRoute = placeService.findAlongRoute(origin, destination, travelTime,
                user.getUser().getFavoriteCategories());

        model.addAttribute("alongRoute", alongRoute);
        model.addAttribute("direction", "There");

        return "/app/addTrip2";
    }

    @PostMapping("/add-trip2")
    public String addTrip2(@ModelAttribute Trip trip,
                           @RequestParam String direction,
                           Model model) {

        Trip forUpdate = tripService.findTripById(trip.getId());
        List<Activity> activities = forUpdate.getActivities();
        int start;
        if ("There".equals(direction)) {
            start = 1;
        } else { // "Back"
            start = activities.size() - 1;
        }
        activities.addAll(start, trip.getActivities());

        for (int i = start; i < activities.size(); i++) {
            Activity current = activities.get(i);
            Activity previous = activities.get(i - 1);
            current.setArrival(previous.getDeparture()
                    .plusMinutes(routeService.calculateRouteTime(previous.getPlace(), current.getPlace()) / 60));
            current.setDeparture(current.getArrival()); // no duration yet
        }

        model.addAttribute("trip", forUpdate);
        model.addAttribute("direction", direction);
        model.addAttribute("start", start);
        return "/app/addTrip3";
    }

    @PostMapping("/add-trip3")
    public String addTrip3(@ModelAttribute Trip trip,
                         @RequestParam String direction,
                         @RequestParam Integer start,
                         @AuthenticationPrincipal CurrentUser user,
                         Model model) {
        Trip forUpdate = tripService.findTripById(trip.getId());

        forUpdate.getActivities().addAll(start,
                trip.getActivities().stream()
                        .filter(a -> a.getId() == null)
                        // This peek modifies stream elements
                        .peek(a ->a.setDeparture(a.getArrival().plusMinutes(a.getDuration())))
                        .collect(Collectors.toList()));
        forUpdate = tripService.save(forUpdate);
        model.addAttribute("trip", forUpdate);

        if("There".equals(direction)) {
            List<Activity> activities = forUpdate.getActivities();
            Place origin = activities.get(0).getPlace();
            Place destination = activities.get(activities.size()-2).getPlace();
            int travelTime = routeService.calculateRouteTime(destination,origin);
            List<Place> alongRoute = placeService.findAlongRoute(destination,origin, travelTime,
                    user.getUser().getFavoriteCategories());
            model.addAttribute("alongRoute", alongRoute);
            model.addAttribute("direction", "Back");
            return "/app/addTrip2";
        } else {
            return "/app/addTrip4";
        }
    }
}
