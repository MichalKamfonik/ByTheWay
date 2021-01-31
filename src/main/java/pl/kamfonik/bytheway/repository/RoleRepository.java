package pl.kamfonik.bytheway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamfonik.bytheway.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
