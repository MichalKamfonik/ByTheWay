package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.converter.Entity2DtoConverter;
import pl.kamfonik.bytheway.dto.Route;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.entity.*;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.*;
import pl.kamfonik.bytheway.validator.UserFormValidation;

import javax.validation.groups.Default;
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
    private final PlaceService placeService;
    private final TripService tripService;
    private final ByTheWayProperties byTheWayProperties;
    private final RouteService routeService;
    private final Entity2DtoConverter<Place, PlaceDto> placeEntity2DtoConverter;

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
        model.addAttribute("plan", new Plan());
        return "app/dashboard";
    }

    @GetMapping("/categories")
    public String manageCategoriesForm(Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("user", user.getUser());
        return "app/categories";
    }

    @PostMapping("/categories")
    public String manageCategories(@Validated({UserFormValidation.class}) @ModelAttribute User user,
                                   BindingResult result,
                                   Model model,
                                   @AuthenticationPrincipal CurrentUser currentUser) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllCategories());
            return "app/categories";
        }
        User currentUserUser = currentUser.getUser();
        currentUserUser.setFavoriteCategories(user.getFavoriteCategories());
        userService.updateUser(currentUserUser);
        return "redirect:/app";
    }

    @PostMapping("/add-plan")
    public String addPlanForm(
            @ModelAttribute @Validated({Default.class, UserFormValidation.class}) Plan plan,
            BindingResult result,
            @AuthenticationPrincipal CurrentUser currentUser,
            Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> log.debug(e.toString()));
            result.addError(new ObjectError("plan", "Incorrect data, please try again"));
            User user = currentUser.getUser();
            model.addAttribute("plans", planService.findUserPlans(user));
            model.addAttribute("trips", tripService.findUserTrips(user));
            model.addAttribute("categories", user.getFavoriteCategories());
            return "app/dashboard";
        }
        plan.setEndTime(plan.getStartTime().plusDays(plan.getTrip().getDuration()));
        plan.setUser(currentUser.getUser());
        planService.save(plan);

        return "redirect:/app";
    }

    @PostMapping("/delete/trip/{id}")
    public String deleteTrip(Model model,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             @PathVariable Long id) {
        User user = currentUser.getUser();
        if (user.isAdmin() || tripService.checkUserTrip(id, user)) {
            Trip trip = tripService.findTripById(id);
            List<Plan> plans = planService.findPlansByTrip(trip);
            if (plans.isEmpty()) {
                tripService.delete(id);
            } else {
                model.addAttribute("plans", plans);
                return "/app/deleteTripImpossible";
            }
        } else {
            return "redirect:/403";
        }
        return "redirect:/app";
    }

    @PostMapping("/delete/plan/{id}")
    public String deletePlan(@AuthenticationPrincipal CurrentUser currentUser,
                             @PathVariable Long id) {
        User user = currentUser.getUser();
        if (user.isAdmin() || planService.checkUserPlan(id, user)) {
            planService.delete(id);
        } else {
            return "redirect:/403";
        }
        return "redirect:/app";
    }

    @GetMapping("/show/trip/{id}")
    public String showTrip(Model model,
                           @AuthenticationPrincipal CurrentUser currentUser,
                           @PathVariable Long id) {
        User user = currentUser.getUser();
        if (user.isAdmin() || tripService.checkUserTrip(id, user)) {
            Trip trip = tripService.findTripById(id);
            Route route = routeService.getRoute(
                    trip.getActivities().stream()
                            .map(Activity::getPlace)
                            .map(placeEntity2DtoConverter::convert)
                            .collect(Collectors.toList())
            );

            model.addAttribute("trip", trip);
            model.addAttribute("mappingApiKey", byTheWayProperties.getMapping().getApikey());
            model.addAttribute("mapDataObject", route.getRouteObjectForMapping());

            return "/app/showTrip";
        } else {
            return "redirect:/403";
        }
    }

    @GetMapping("/add-trip")
    public String addTripForm(Model model) {
        model.addAttribute("trip", new Trip());
        model.addAttribute("mappingApiKey", byTheWayProperties.getMapping().getApikey());
        return "app/tripForm";
    }

    @PostMapping("/add-trip")
    public String addTrip(
            @Validated({Default.class, UserFormValidation.class}) @ModelAttribute Trip trip,
            BindingResult result,
            @AuthenticationPrincipal CurrentUser currentUser,
            Model model) {
        if (result.hasErrors()) {
            result.addError(new ObjectError("trip", "Incorrect data, please try again"));
            model.addAttribute("mappingApiKey", byTheWayProperties.getMapping().getApikey());
            return "app/tripForm";
        }
        trip.setUser(currentUser.getUser());
        tripService.save(trip);
        return "redirect:/app";
    }
}
