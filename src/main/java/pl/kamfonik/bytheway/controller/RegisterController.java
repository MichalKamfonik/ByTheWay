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
import pl.kamfonik.bytheway.dto.app.UserDto;
import pl.kamfonik.bytheway.exception.RegistrationDisabledException;
import pl.kamfonik.bytheway.exception.RepeatedPasswordException;
import pl.kamfonik.bytheway.exception.UserNameTakenException;
import pl.kamfonik.bytheway.service.interfaces.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "login/register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute(name = "user") @Valid UserDto userDto, BindingResult result) {
        try {
            if(!result.hasErrors()){
                userService.registerUser(userDto);
                return "login/registrySuccess";
            }
        } catch (UserNameTakenException e) {
            result.addError(new FieldError(
                            "user",
                            "username",
                            "Username already taken. Choose other name"
                    )
            );
        } catch (RepeatedPasswordException e) {
            result.addError(new FieldError(
                            "user",
                            "repeatedPassword",
                            "Repeated password must be the same as password"
                    )
            );
        } catch (RegistrationDisabledException e) {
            return "login/registryFail";
        }
        return "login/register";
    }
}
