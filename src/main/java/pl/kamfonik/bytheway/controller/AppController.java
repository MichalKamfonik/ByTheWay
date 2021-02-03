package pl.kamfonik.bytheway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.security.CurrentUser;
import pl.kamfonik.bytheway.service.CategoryService;
import pl.kamfonik.bytheway.service.PlanService;
import pl.kamfonik.bytheway.service.UserService;

@Controller
@Slf4j
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
        userService.saveUser(currentUserUser);
        return "redirect:/app";
    }
}
