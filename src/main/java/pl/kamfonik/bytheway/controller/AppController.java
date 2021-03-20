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
import pl.kamfonik.bytheway.entity.Plan;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.interfaces.CategoryService;
import pl.kamfonik.bytheway.service.interfaces.PlanService;
import pl.kamfonik.bytheway.service.interfaces.TripService;
import pl.kamfonik.bytheway.service.interfaces.UserService;
import pl.kamfonik.bytheway.validator.UserFormValidation;

import javax.validation.groups.Default;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/app")
public class AppController {
    private final PlanService planService;
    private final CategoryService categoryService;
    private final UserService userService;
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


    @PostMapping("/delete/plan/{id}")
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
