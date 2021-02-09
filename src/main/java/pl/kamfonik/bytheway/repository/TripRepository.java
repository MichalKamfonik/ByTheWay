package pl.kamfonik.bytheway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamfonik.bytheway.entity.Trip;

public interface TripRepository extends JpaRepository<Trip,Long> {
}
