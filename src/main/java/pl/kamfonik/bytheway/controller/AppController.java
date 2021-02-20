package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.entity.*;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.*;

import javax.validation.Valid;

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
    public String manageCategories(@Valid @ModelAttribute User user,
                                   BindingResult result,
                                   @AuthenticationPrincipal CurrentUser currentUser) {
        if(result.hasErrors()){
            return "app/categories";
        }
        User currentUserUser = currentUser.getUser();
        currentUserUser.setFavoriteCategories(user.getFavoriteCategories());
        userService.updateUser(currentUserUser);
        return "redirect:/app";
    }

    @GetMapping("/add-plan")
    public String addPlanForm(Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("plan", new Plan());
        model.addAttribute("trips", tripService.findUserTrips(user.getUser()));
        model.addAttribute("plans", planService.findUserPlans(user.getUser()));

        return "/app/addPlan";
    }

    @PostMapping("/add-plan")
    public String addPlanForm(@ModelAttribute Plan plan, @AuthenticationPrincipal CurrentUser user) {
        plan.setEndTime(plan.getStartTime().plusDays(plan.getTrip().getDuration()));
        plan.setUser(user.getUser());
        planService.save(plan);

        return "redirect:/app";
    }

    @GetMapping("/delete/trip/{id}")
    public String confirmDeleteTrip(Model model,
                                    @AuthenticationPrincipal CurrentUser currentUser,
                                    @PathVariable Long id) {
        User user = currentUser.getUser();
        if (user.isAdmin() || tripService.checkUserTrip(id, user)) {
            model.addAttribute("trip", tripService.findTripById(id));
            return "/app/confirmDeleteTrip";
        } else {
            return "redirect:/403";
        }
    }

    @PostMapping("/delete/trip/{id}")
    public String deleteTrip(@AuthenticationPrincipal CurrentUser currentUser,
                             @PathVariable Long id,
                             @RequestParam String choice) {
        if ("No".equals(choice)) {
            return "redirect:/app";
        }
        User user = currentUser.getUser();
        if (user.isAdmin() || tripService.checkUserTrip(id, user)) {
            tripService.delete(id);
        } else {
            return "redirect:/403";
        }
        return "redirect:/app";
    }

    @GetMapping("/delete/plan/{id}")
    public String confirmDeletePlan(Model model,
                                    @AuthenticationPrincipal CurrentUser currentUser,
                                    @PathVariable Long id) {
        User user = currentUser.getUser();
        if (user.isAdmin() || planService.checkUserPlan(id, user)) {
            model.addAttribute("plan", planService.findPlanById(id));
            return "/app/confirmDeletePlan";
        } else {
            return "redirect:/403";
        }
    }

    @PostMapping("/delete/plan/{id}")
    public String deletePlan(@AuthenticationPrincipal CurrentUser currentUser,
                             @PathVariable Long id,
                             @RequestParam String choice) {
        if ("No".equals(choice)) {
            return "redirect:/app";
        }
        User user = currentUser.getUser();
        if (user.isAdmin() || planService.checkUserPlan(id, user)) {
            planService.delete(id);
        } else {
            return "redirect:/403";
        }
        return "redirect:/app";
    }

    @GetMapping("/add-trip")
    public String addTripForm(Model model) {
        model.addAttribute("trip", new Trip());
        return "app/tripForm";
    }

    @PostMapping("/add-trip")
    public String addTrip(@RequestBody Trip trip, @AuthenticationPrincipal CurrentUser currentUser) {
        trip.setUser(currentUser.getUser());
        tripService.save(trip);
        return "redirect:/app";
    }
}
