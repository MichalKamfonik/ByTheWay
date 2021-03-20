package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
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
import pl.kamfonik.bytheway.service.interfaces.PlanService;
import pl.kamfonik.bytheway.service.interfaces.RouteService;
import pl.kamfonik.bytheway.service.interfaces.TripService;
import pl.kamfonik.bytheway.validator.UserFormValidation;

import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private final PlanService planService;
    private final RouteService routeService;
    private final Entity2DtoConverter<Place, PlaceDto> placeEntity2DtoConverter;
    private final ByTheWayProperties byTheWayProperties;

    @PostMapping("/delete/{id}")
    public String deleteTrip(Model model,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             @PathVariable Long id) {
        User user = currentUser.getUser();
        if (tripService.checkUserTrip(id, user)) {
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

    @GetMapping("/show/{id}")
    public String showTrip(Model model,
                           @AuthenticationPrincipal CurrentUser currentUser,
                           @PathVariable Long id) {
        User user = currentUser.getUser();
        if (tripService.checkUserTrip(id, user)) {
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

    @GetMapping("/add")
    public String addTripForm(Model model) {
        model.addAttribute("trip", new Trip());
        model.addAttribute("mappingApiKey", byTheWayProperties.getMapping().getApikey());
        return "app/tripForm";
    }

    @PostMapping("/add")
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
