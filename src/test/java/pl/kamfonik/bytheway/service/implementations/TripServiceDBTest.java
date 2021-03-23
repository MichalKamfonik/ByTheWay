package pl.kamfonik.bytheway.service.implementations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kamfonik.bytheway.entity.Activity;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.repository.TripRepository;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceDBTest {

    @Mock
    private PlaceService placeService;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripServiceDB tripServiceDB;

    @Test
    void shouldFailWhenSaveToRepository() {
        assertThrows(RuntimeException.class, () -> tripServiceDB.save(new Trip()));
    }

    @Test
    void shouldSaveToRepository(){
        Trip trip = new Trip();
        Activity activity = new Activity();
        activity.setPlace(new Place());
        Activity activity1 = new Activity();
        activity1.setPlace(new Place());
        trip.setActivities(Arrays.asList(activity, activity1));
        when(tripRepository.save(trip)).thenReturn(trip);

        Optional<Trip> save = tripServiceDB.save(trip);

        assertTrue(save.isPresent());
        verify(placeService, times(2)).save(any());
    }

    @Test
    void shouldFailToSaveToRepository(){
        Trip trip = new Trip();
        Activity activity = new Activity();
        activity.setPlace(new Place());
        Activity activity1 = new Activity();
        activity1.setPlace(new Place());
        trip.setActivities(Arrays.asList(activity, activity1));
        when(tripRepository.save(trip)).thenThrow(new IllegalArgumentException());

        Optional<Trip> save = tripServiceDB.save(trip);

        assertTrue(save.isEmpty());
        verify(placeService, times(2)).save(any());
    }
}