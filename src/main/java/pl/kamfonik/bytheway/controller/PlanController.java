package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kamfonik.bytheway.entity.Plan;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.entity.CurrentUser;
import pl.kamfonik.bytheway.service.interfaces.PlanService;
import pl.kamfonik.bytheway.service.interfaces.TripService;
import pl.kamfonik.bytheway.validator.UserFormValidation;

import javax.validation.groups.Default;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/plan")
public class PlanController {
    private final PlanService planService;
    private final TripService tripService;

    @PostMapping("/add")
    public String addPlanForm(
            @ModelAttribute @Validated({Default.class, UserFormValidation.class}) Plan plan,
            BindingResult result,
            @AuthenticationPrincipal CurrentUser currentUser,
            Model model) {
        if (result.hasErrors()) {
            result.addError(new ObjectError("plan", "Incorrect data, please try again"));
            User user = currentUser.getUser();
            model.addAttribute("plans", planService.findUserPlans(user));
            model.addAttribute("trips", tripService.findUserTrips(user));
            model.addAttribute("categories", user.getFavoriteCategories());
            return "app/dashboard";
        }
        plan.setEndTime(plan.getStartTime().plusDays(plan.getTrip().getDuration()-1));
        plan.setUser(currentUser.getUser());
        planService.save(plan);

        return "redirect:/app";
    }

    @PostMapping("/delete/{id}")
    public String deletePlan(@AuthenticationPrincipal CurrentUser currentUser,
                             @PathVariable Long id) {
        User user = currentUser.getUser();
        if (planService.checkUserPlan(id, user)) {
            planService.delete(id);
        } else {
            return "redirect:/403";
        }
        return "redirect:/app";
    }
}
