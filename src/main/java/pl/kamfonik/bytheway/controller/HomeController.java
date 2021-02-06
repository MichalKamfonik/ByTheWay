package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.service.UserService;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping
    public String home(){
        return "home";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user",new User());
        return "login/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam String selection){
        if("Cancel".equals(selection)){
            return "redirect:";
        }
        if (userService.findByUsername(user.getUsername()) != null){
            return "login/userNameTaken";
        }
        if(userService.saveUser(user)){
            return "login/registrySuccess";
        } else {
            return "login/registryFail";
        }
    }
}