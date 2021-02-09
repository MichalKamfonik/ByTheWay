package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.repository.TripRepository;

@Service
@RequiredArgsConstructor
public class TripServiceDB implements TripService{
    private final TripRepository tripRepository;

    @Override
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip findTripById(Long id) {
        return tripRepository.findById(id).orElseThrow();
    }
}