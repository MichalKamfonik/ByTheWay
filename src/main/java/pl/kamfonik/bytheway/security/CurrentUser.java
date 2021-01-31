package pl.kamfonik.bytheway.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CurrentUser extends User {
    private final pl.kamfonik.bytheway.entity.User user;
    public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.kamfonik.bytheway.entity.User user) {
        super(username, password, authorities);
        this.user = user;
    }
    public pl.kamfonik.bytheway.entity.User getUser() {return user;}
}
