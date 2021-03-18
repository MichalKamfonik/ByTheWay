package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String name);
    boolean saveUser(User user);
    void updateUser(User user);
}
