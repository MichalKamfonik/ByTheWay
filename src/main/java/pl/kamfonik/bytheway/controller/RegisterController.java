package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "login/register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute @Valid User user, BindingResult result) {
        if (!passwordsMatch(user)) {
            result.addError(
                    new FieldError(
                            "user",
                            "repeatedPassword",
                            "Repeated password must be the same as password"
                    )
            );
        }
        if (userExists(user)) {
            result.addError(
                    new FieldError(
                            "user",
                            "username",
                            "Username already taken. Choose other name"
                    )
            );
        }
        if(result.hasErrors()){
            return "login/register";
        }

        if (userService.saveUser(user)) {
            return "login/registrySuccess";
        } else {
            return "login/registryFail";
        }
    }

    private boolean userExists(User user) {
        return userService.findByUsername(user.getUsername()).isPresent();
    }

    private boolean passwordsMatch(User user) {
        return user.getInitialPassword().equals(user.getRepeatedPassword());
    }
}
