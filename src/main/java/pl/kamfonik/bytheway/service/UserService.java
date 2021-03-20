package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.dto.app.UserDto;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.exception.RegistrationDisabledException;
import pl.kamfonik.bytheway.exception.RepeatedPasswordException;
import pl.kamfonik.bytheway.exception.UserNameTakenException;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String name);
    void registerUser(UserDto userDto) throws RepeatedPasswordException, UserNameTakenException, RegistrationDisabledException;
    void updateUser(User user);
}
