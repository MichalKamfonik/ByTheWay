package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kamfonik.bytheway.entity.Plan;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.entity.CurrentUser;
import pl.kamfonik.bytheway.service.interfaces.PlanService;
import pl.kamfonik.bytheway.service.interfaces.TripService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/app")
public class DashboardController {
    private final PlanService planService;
    private final TripService tripService;

    @GetMapping
    public String dashboard(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        model.addAttribute("plans", planService.findUserPlans(user));
        model.addAttribute("trips", tripService.findUserTrips(user));
        model.addAttribute("categories", user.getFavoriteCategories());
        model.addAttribute("plan", new Plan());
        return "app/dashboard";
    }
}
