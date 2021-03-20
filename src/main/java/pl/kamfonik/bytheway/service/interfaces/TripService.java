package pl.kamfonik.bytheway.service.interfaces;

import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;

import java.util.List;
import java.util.Optional;

public interface TripService {

    Optional<Trip> save(Trip trip);

    Optional<Trip> findTripById(Long id);

    List<Trip> findUserTrips(User user);

    Boolean checkUserTrip(Long id, User user);

    void delete(Long id);
}
