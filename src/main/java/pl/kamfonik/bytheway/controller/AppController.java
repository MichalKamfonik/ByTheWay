package pl.kamfonik.bytheway.controller;

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
import pl.kamfonik.bytheway.service.UserService;

@Controller
@Slf4j
@SessionAttributes("newTrip")
@RequestMapping("/app")
public class AppController {
    private final PlanService planService;
    private final CategoryService categoryService;
    private final UserService userService;

    public AppController(PlanService planService, CategoryService categoryService, UserService userService) {
        this.planService = planService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(Model model, @AuthenticationPrincipal CurrentUser currentUser){
        User user = currentUser.getUser();
        model.addAttribute("plans",planService.findUserPlans(user));
        model.addAttribute("categories", user.getFavoriteCategories());
        return "app/dashboard";
    }

    @GetMapping("/categories")
    public String manageCategoriesForm(Model model, @AuthenticationPrincipal CurrentUser user){
        model.addAttribute("categories",categoryService.findAllCategories());
        model.addAttribute("user",user.getUser());
        return "app/categories";
    }

    @PostMapping("/categories")
    public String manageCategories(@ModelAttribute User user, @AuthenticationPrincipal CurrentUser currentUser){
        User currentUserUser = currentUser.getUser();
        currentUserUser.setFavoriteCategories(user.getFavoriteCategories());
        userService.updateUser(currentUserUser);
        return "redirect:/app";
    }

    @GetMapping("/addTrip")
    public String addTripForm(Model model){
        if(model.getAttribute("newTrip")==null){
            model.addAttribute("newTrip",new Trip());
        }
        return "app/addTrip";
    }
    @PostMapping("/addTrip")
    public String addTrip(@ModelAttribute Trip trip, Model model){
        model.addAttribute("newTrip",trip);
        log.debug(model.getAttribute("newTrip").toString());
        return "redirect:/app";
    }
}
