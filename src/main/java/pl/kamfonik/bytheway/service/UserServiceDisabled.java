package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "bytheway", name = "registration", havingValue = "disabled")
@Slf4j
@Service
public class UserServiceDisabled implements UserService{
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public boolean saveUser(User user) {
        log.info("User registration is disabled.");
        return false;
    }

    @Override
    public void updateUser(User user) {
        if(userRepository.existsById(user.getId())){ // was checked in controller but "szczerzonego Pan Bóg szczerze xD"
            userRepository.save(user);
        }
    }
}
