package pl.kamfonik.bytheway.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.entity.Activity;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.repository.TripRepository;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;
import pl.kamfonik.bytheway.service.interfaces.TripService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripServiceDB implements TripService {
    private final TripRepository tripRepository;
    private final PlaceService placeService;

    @Override
    public Optional<Trip> save(Trip trip) {
        trip.getActivities().stream()
                .map(Activity::getPlace)
                .forEach(placeService::save);
        try {
            return Optional.of(tripRepository.save(trip));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Trip> findTripById(Long id) {
        return tripRepository.findById(id);
    }

    @Override
    public List<Trip> findUserTrips(User user) {
        return tripRepository.findByUser(user);
    }

    @Override
    public Boolean checkUserTrip(Long id, User user) {
        return findTripById(id)
                .map(Trip::getUser)
                .map(User::getId)
                .map(userId -> userId.equals(user.getId()))
                .orElse(false);
    }

    @Override
    public void delete(Long id) {
        tripRepository.deleteById(id);
    }
}