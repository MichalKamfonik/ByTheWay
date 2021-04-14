package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.entity.*;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.*;

import java.time.Duration;
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
        if (user.getUser().isAdmin()) {
            categoryService.initialize();
        } else {
            return "redirect:/403";
        }
        return "redirect:/app";
    }

    @GetMapping("/clear-places")
    public String clearPlaces(@AuthenticationPrincipal CurrentUser user) {
        if (user.getUser().isAdmin()) {
            placeService.clear();
        } else {
            return "redirect:/403";
        }
        return "redirect:/app";
    }

    @GetMapping
    public String dashboard(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        model.addAttribute("plans", planService.findUserPlans(user));
        model.addAttribute("trips", tripService.findUserTrips(user));
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
        int travelTimeThere = routeService.calculateRouteTime(origin, destination);
        int travelTimeBack = routeService.calculateRouteTime(destination, origin);

        trip = tripService.initialize(trip, origin, destination, travelTimeThere, travelTimeBack, user.getUser());

        model.addAttribute("trip", trip);

        List<Place> alongRoute = placeService.findAlongRoute(origin, destination, travelTimeThere,
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
        Activity destination;
        int start;
        if ("There".equals(direction)) {
            start = 1;
            destination = activities.get(start);
        } else { // "Back"
            start = activities.size() - 1;
            destination = activities.get(start - 1);
        }

        activities.addAll(start, trip.getActivities());

        if ("There".equals(direction)) {
            for (int i = start; i < activities.size() - 1; i++) {
                Activity current = activities.get(i);
                Activity previous = activities.get(i - 1);
                current.setArrival(previous.getDeparture()
                        .plusMinutes(routeService.calculateRouteTime(previous.getPlace(), current.getPlace()) / 60));
                if (!destination.equals(current)) {
                    current.setDeparture(current.getArrival()); // no duration yet
                } else { // update time left for destination activity - departure is fixed for now
                    current.setDuration(Long.valueOf(
                            Duration.between(current.getArrival(), current.getDeparture()).toMinutes()
                                    + (forUpdate.getDuration() - 1) * 24L * 60L).intValue());
                }
            }
        } else { // Back
            for (int i = activities.size() - 2; i >= start - 1; i--) {
                Activity current = activities.get(i);
                Activity next = activities.get(i + 1);
                current.setDeparture(next.getArrival()
                        .minusMinutes(routeService.calculateRouteTime(current.getPlace(), next.getPlace()) / 60));
                if (!destination.equals(current)) {
                    current.setArrival(current.getDeparture()); // no duration yet
                } else { // update time left for destination activity - departure is fixed for now
                    current.setDuration(Long.valueOf(
                            Duration.between(current.getArrival(), current.getDeparture()).toMinutes()
                                    + (forUpdate.getDuration() - 1) * 24L * 60L).intValue());
                }
            }
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

        List<Activity> activities = forUpdate.getActivities();
        Activity destination;

        List<Activity> activitiesFromView = trip.getActivities();

        activities.addAll(start,activitiesFromView.stream()
                        .filter(a -> a.getId() == null)
                        .collect(Collectors.toList()));

        int summaryDuration = 0;
        if ("There".equals(direction)) {
            destination = activities.get(activities.size()-2);
            destination.setArrival(activitiesFromView.get(activitiesFromView.size()-2).getArrival());
            destination.setDuration(activitiesFromView.get(activitiesFromView.size()-2).getDuration());
            for (int i = start; i < activities.size() - 1; i++) {
                Activity current = activities.get(i);
                current.setNumber(i);
                current.setArrival(current.getArrival().plusMinutes(summaryDuration));
                if (!destination.equals(current)) {
                    current.setDeparture(current.getArrival().plusMinutes(current.getDuration()));
                } else { // update time left for destination activity - departure is fixed for now
                    current.setDuration(current.getDuration() - summaryDuration);
                }
                summaryDuration += current.getDuration();
            }
        } else { // Back
            destination = activities.get(start-1);
            destination.setDeparture(activitiesFromView.get(start-1).getDeparture());
            destination.setDuration(activitiesFromView.get(start-1).getDuration());
            for (int i = activities.size() - 2; i >= start-1; i--) {
                Activity current = activities.get(i);
                current.setNumber(i);
                current.setDeparture(current.getDeparture().minusMinutes(summaryDuration));
                if (!destination.equals(current)) {
                    current.setArrival(current.getDeparture().minusMinutes(current.getDuration()));
                } else { // update time left for destination activity - departure is fixed for now
                    current.setDuration(current.getDuration() - summaryDuration);
                }
                summaryDuration += current.getDuration();
            }
        }
        activities.get(activities.size()-1).setNumber(activities.size()-1);

        forUpdate = tripService.save(forUpdate);
        model.addAttribute("trip", forUpdate);

        if ("There".equals(direction)) {
            Place originPlace = activities.get(0).getPlace();
            Place destinationPlace = destination.getPlace();
            int travelTime = Long.valueOf(
                    Duration.between(destination.getDeparture(), forUpdate.getArrival()).toMinutes()
            ).intValue();
            List<Place> alongRoute = placeService.findAlongRoute(destinationPlace, originPlace, travelTime*60,
                    user.getUser().getFavoriteCategories());
            model.addAttribute("alongRoute", alongRoute);
            model.addAttribute("direction", "Back");
            return "/app/addTrip2";
        } else {
            return "/app/addTrip4";
        }
    }

    @GetMapping("/add-plan")
    public String addPlanForm(Model model, @AuthenticationPrincipal CurrentUser user){
        model.addAttribute("plan", new Plan());
        model.addAttribute("trips",tripService.findUserTrips(user.getUser()));
        model.addAttribute("plans",planService.findUserPlans(user.getUser()));

        return "/app/addPlan";
    }
    @PostMapping("/add-plan")
    public String addPlanForm(@ModelAttribute Plan plan, @AuthenticationPrincipal CurrentUser user){
        plan.setEndTime(plan.getStartTime().plusDays(plan.getTrip().getDuration()));
        plan.setUser(user.getUser());
        planService.save(plan);

        return "redirect:/app";
    }

    @GetMapping("/delete/trip/{id}")
    public String confirmDeleteTrip(Model model,
                                    @AuthenticationPrincipal CurrentUser currentUser,
                                    @PathVariable Long id){
        User user = currentUser.getUser();
        if (user.isAdmin() || tripService.checkUserTrip(id,user)) {
//            model.addAttribute("trip",tripService.findTripById(id));
//            return "/app/confirmDeleteTrip";
            tripService.delete(id);
            return "redirect:/app";
        } else {
            return "redirect:/403";
        }
    }

//    @PostMapping("/delete/trip/{id}")
//    public String deleteTrip(@AuthenticationPrincipal CurrentUser currentUser,
//                             @PathVariable Long id,
//                             @RequestParam String choice){
//        if("No".equals(choice)){
//            return "redirect:/app";
//        }
//        User user = currentUser.getUser();
//        if (user.isAdmin() || tripService.checkUserTrip(id,user)) {
//            tripService.delete(id);
//        } else {
//            return "redirect:/403";
//        }
//        return "redirect:/app";
//    }

    @GetMapping("/delete/plan/{id}")
    public String confirmDeletePlan(Model model,
                                    @AuthenticationPrincipal CurrentUser currentUser,
                                    @PathVariable Long id){
        User user = currentUser.getUser();
        if (user.isAdmin() || planService.checkUserPlan(id,user)) {
//            model.addAttribute("plan",planService.findPlanById(id));
//            return "/app/confirmDeletePlan";
            planService.delete(id);
            return "redirect:/app";
        } else {
            return "redirect:/403";
        }
    }

//    @PostMapping("/delete/plan/{id}")
//    public String deletePlan(@AuthenticationPrincipal CurrentUser currentUser,
//                             @PathVariable Long id,
//                             @RequestParam String choice){
//        if("No".equals(choice)){
//            return "redirect:/app";
//        }
//        User user = currentUser.getUser();
//        if (user.isAdmin() || planService.checkUserPlan(id,user)) {
//            planService.delete(id);
//        } else {
//            return "redirect:/403";
//        }
//        return "redirect:/app";
//    }
}
