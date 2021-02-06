package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.CategoryService;
import pl.kamfonik.bytheway.service.PlanService;
import pl.kamfonik.bytheway.service.RouteService;
import pl.kamfonik.bytheway.service.UserService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/app")
public class AppController {
    private final PlanService planService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final RouteService routeService;

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

    @GetMapping("/addTrip1")
    public String addTripForm1(Model model) {
        model.addAttribute("trip", new Trip());
        return "app/addTrip1";
    }

    @PostMapping("/addTrip1")
    public String addTrip1(@ModelAttribute Trip trip, Model model) {
        model.addAttribute("trip", trip);

        int travelTime = routeService.calculateRouteTime(trip.getOrigin(), trip.getDestination()) / 60;
        model.addAttribute("travelTime", travelTime);

        return "/app/addTrip2";
    }
}
