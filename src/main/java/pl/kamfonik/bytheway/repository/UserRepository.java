package pl.kamfonik.bytheway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamfonik.bytheway.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String name);
}
