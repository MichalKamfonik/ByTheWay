package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.entity.User;

public interface UserService {
    User findByUsername(String name);
    void saveUser(User user);
    void updateUser(User user);
}
