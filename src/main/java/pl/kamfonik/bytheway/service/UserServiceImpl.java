package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.dto.app.UserDto;
import pl.kamfonik.bytheway.entity.Role;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.exception.RepeatedPasswordException;
import pl.kamfonik.bytheway.exception.UserNameTakenException;
import pl.kamfonik.bytheway.repository.RoleRepository;
import pl.kamfonik.bytheway.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "bytheway", name = "registration", havingValue = "enabled")
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void registerUser(UserDto userDto) throws  UserNameTakenException, RepeatedPasswordException{
        if (userExists(userDto)){
            throw new UserNameTakenException();
        }
        if(!passwordsMatch(userDto)){
            throw new RepeatedPasswordException();
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFavoriteCategories(new ArrayList<>());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }



    private boolean userExists(UserDto userDto) {
        return findByUsername(userDto.getUsername()).isPresent();
    }

    private boolean passwordsMatch(UserDto userDto) {
        return userDto.getPassword().equals(userDto.getRepeatedPassword());
    }
}
