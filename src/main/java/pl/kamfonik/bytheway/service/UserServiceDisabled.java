package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.dto.app.UserDto;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.exception.RegistrationDisabledException;
import pl.kamfonik.bytheway.repository.UserRepository;
import pl.kamfonik.bytheway.service.interfaces.UserService;

import java.util.Optional;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "bytheway", name = "registration", havingValue = "disabled")
@Slf4j
@Service
public class UserServiceDisabled implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void registerUser(UserDto userDto) throws RegistrationDisabledException{
        log.info("User registration is disabled.");
        throw new RegistrationDisabledException();
    }

    @Override
    public void updateUser(User user) {
        if(userRepository.existsById(user.getId())){ // was checked in controller but "just in case"
            userRepository.save(user);
        }
    }
}
