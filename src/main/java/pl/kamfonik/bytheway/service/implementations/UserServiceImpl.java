package pl.kamfonik.bytheway.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.entity.Role;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.repository.RoleRepository;
import pl.kamfonik.bytheway.repository.UserRepository;
import pl.kamfonik.bytheway.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "bytheway", name = "registration", havingValue = "enabled")
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public boolean registerUser(User user){
        user.setFavoriteCategories(new ArrayList<>());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
        return true;
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
