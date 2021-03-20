package pl.kamfonik.bytheway.service.interfaces;

import pl.kamfonik.bytheway.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String name);
    boolean registerUser(User user);
    void updateUser(User user);
}
