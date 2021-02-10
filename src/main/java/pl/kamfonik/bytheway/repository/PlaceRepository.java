package pl.kamfonik.bytheway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamfonik.bytheway.entity.Place;

public interface PlaceRepository extends JpaRepository<Place,String> {
}
