package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.entity.Activity;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface TripService {

    default Trip initialize(Trip trip, Place origin, Place destination,
                            Integer travelTimeThere, Integer travelTimeBack, User user) {

        travelTimeThere /= 60; // to minutes
        travelTimeBack /= 60; // to minutes

        LocalTime departure = trip.getDeparture();
        LocalTime arrival = trip.getArrival();

        int tripDuration = Long.valueOf(
                Duration.between(departure,arrival).toMinutes() + (trip.getDuration()-1) * 24L * 60L
        ).intValue();

        Activity start = new Activity(
                "__ORIGIN__",
                origin,
                0,
                departure,
                departure,
                0
        );
        Activity goal = new Activity(
                "__DESTINATION__",
                destination,
                tripDuration - travelTimeThere - travelTimeBack,
                departure.plusMinutes(travelTimeThere),
                arrival.minusMinutes(travelTimeBack),
                1
        );
        Activity end = new Activity(
                "__ORIGIN__",
                origin,
                0,
                arrival,
                arrival,
                2
        );
        List<Activity> activities = new ArrayList<>();
        activities.add(start);
        activities.add(goal);
        activities.add(end);
        trip.setActivities(activities);

        trip.setId(null);
        trip.setUser(user);
        return save(trip);
    }

    Trip save(Trip trip);

    Trip findTripById(Long id);

    List<Trip> findUserTrips(User user);

    Boolean checkUserTrip(Long id, User user);

    void delete(Long id);
}
