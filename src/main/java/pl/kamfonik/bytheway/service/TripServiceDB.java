package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.entity.Activity;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.repository.TripRepository;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;
import pl.kamfonik.bytheway.service.interfaces.TripService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceDB implements TripService {
    private final TripRepository tripRepository;
    private final PlaceService placeService;

    @Override
    public Trip save(Trip trip) {
        trip.getActivities().stream()
                .map(Activity::getPlace)
                .forEach(placeService::save);
        return tripRepository.save(trip);
    }

    @Override
    public Trip findTripById(Long id) {
        return tripRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Trip> findUserTrips(User user) {
        return tripRepository.findByUser(user);
    }

    @Override
    public Boolean checkUserTrip(Long id, User user) {
        return findTripById(id).getUser().getId().equals(user.getId());
    }

    @Override
    public void delete(Long id) {
        tripRepository.deleteById(id);
    }
}