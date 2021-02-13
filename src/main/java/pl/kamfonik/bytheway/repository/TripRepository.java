package pl.kamfonik.bytheway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip,Long> {
    List<Trip> findByUser(User user);
}
