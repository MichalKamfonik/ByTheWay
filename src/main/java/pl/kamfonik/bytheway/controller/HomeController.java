package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    // to wyrzucic
    @GetMapping
    public String home() {
        return "home";
    }

    
    //zostawic, zmienic nazwe kontrolera
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "login/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid User user, BindingResult result) {
//srodek ifa do metody
        if (user.getInitialPassword()!=null && !user.getInitialPassword().equals(user.getRepeatedPassword())) {
            result.addError(
                    new FieldError(
                            "user",
                            "repeatedPassword",
                            "Repeated password must be the same as password"
                    )
            );
        }
        //tutaj tak samo, używac optionali zamiast porownan do nulla
        if (userService.findByUsername(user.getUsername()) != null) {
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
}
