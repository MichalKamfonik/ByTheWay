package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.entity.Activity;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.entity.Trip;

import java.util.ArrayList;

public interface TripService {

    default Trip initialize(Trip trip, Place origin, Place destination, Integer travelTime){

        int departureInMinutes = trip.getDeparture().getHour()*60 + trip.getDeparture().getMinute();
        int arrivalInMinutes = trip.getArrival().getHour()*60 + trip.getArrival().getMinute();
        int tripDuration = trip.getDuration() * 24 * 60 + arrivalInMinutes - departureInMinutes;

        Activity start = new Activity(
                "__ORIGIN__",
                origin,
                0,
                trip.getDeparture(),
                trip.getDeparture()
        );
        Activity goal = new Activity(
                "__DESTINATION__",
                destination,
                tripDuration - travelTime*2,
                trip.getDeparture().plusMinutes(travelTime),
                trip.getArrival().minusMinutes(travelTime)
        );
        Activity end = new Activity(
                "__ORIGIN__",
                origin,
                0,
                trip.getArrival(),
                trip.getArrival()
        );

        trip.setActivities(new ArrayList<>());
        trip.getActivities().add(start);
        trip.getActivities().add(goal);
        trip.getActivities().add(end);

        return save(trip);
    }

    Trip save(Trip trip);

    Trip findTripById(Long id);
}
