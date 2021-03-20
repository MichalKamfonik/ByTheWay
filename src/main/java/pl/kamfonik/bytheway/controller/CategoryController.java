package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.entity.CurrentUser;
import pl.kamfonik.bytheway.service.interfaces.CategoryService;
import pl.kamfonik.bytheway.service.interfaces.UserService;
import pl.kamfonik.bytheway.validator.UserFormValidation;

@Controller
@RequestMapping("/app/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public String manageCategoriesForm(Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("user", user.getUser());
        return "app/categories";
    }

    @PostMapping
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
}
