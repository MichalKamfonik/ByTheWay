package pl.kamfonik.bytheway.mock;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.security.CurrentUser;

@Controller
@RequestMapping("/mock")
public class MockController {

    @GetMapping
    public String home(){
        return "home";
    }
    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "Admin - admins only";
    }
    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal CurrentUser customUser){
        User entityUser = customUser.getUser();
        return "Hello " + entityUser.toString();
    }
}
