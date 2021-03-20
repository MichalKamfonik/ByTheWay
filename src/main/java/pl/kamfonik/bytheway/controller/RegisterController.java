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
import pl.kamfonik.bytheway.dto.app.RegisterDto;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.service.interfaces.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("user", new RegisterDto());
        return "login/register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute(name = "user") @Valid RegisterDto registerDto, BindingResult result) {
        if (!passwordsMatch(registerDto)) {
            result.addError(new FieldError(
                            "user",
                            "repeatedPassword",
                            "Repeated password must be the same as password"
                    )
            );
        }
        if (userExists(registerDto)) {
            result.addError(new FieldError(
                            "user",
                            "username",
                            "Username already taken. Choose other name"
                    )
            );
        }
        if (result.hasErrors()) {
            return "login/register";
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        if (userService.registerUser(user)) {
            return "login/registrySuccess";
        } else {
            return "login/registryFail";
        }
    }

    private boolean passwordsMatch(RegisterDto registerDto) {
        return registerDto.getPassword().equals(registerDto.getRepeatedPassword());
    }

    private boolean userExists(RegisterDto registerDto) {
        return userService.findByUsername(registerDto.getUsername()).isPresent();
    }
}
