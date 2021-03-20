package pl.kamfonik.bytheway.service.interfaces;

import pl.kamfonik.bytheway.entity.Plan;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;

import java.util.List;
import java.util.Optional;

public interface PlanService {
    List<Plan> findUserPlans(User user);

    Optional<Plan> save(Plan plan);

    Optional<Plan> findPlanById(Long id);

    Boolean checkUserPlan(Long id, User user);

    void delete(Long id);

    List<Plan> findPlansByTrip(Trip trip);
}
