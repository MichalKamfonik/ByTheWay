package pl.kamfonik.bytheway.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.security.CurrentUser;

@Controller
@Slf4j
@RequestMapping("/mock")
public class MockController {

    private final ByTheWayProperties byTheWayProperties;

    public MockController(ByTheWayProperties byTheWayProperties) {
        this.byTheWayProperties = byTheWayProperties;
    }

    @GetMapping
    public String home(){
        log.error(byTheWayProperties.getCategory().getApikey());
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
