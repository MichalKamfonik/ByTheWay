package pl.kamfonik.bytheway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.service.UserService;

@Controller
public class HomeController {
    private final UserService userService;
    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

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
    public String registerUser(@ModelAttribute User user){
        userService.saveUser(user);
        return "redirect:";
    }
}
