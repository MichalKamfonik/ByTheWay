package pl.kamfonik.bytheway.service.interfaces;

import pl.kamfonik.bytheway.entity.Plan;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;

import java.util.List;

public interface PlanService {
    List<Plan> findUserPlans(User user);
    Plan save(Plan plan);

    Plan findPlanById(Long id);

    Boolean checkUserPlan(Long id, User user);

    void delete(Long id);

    List<Plan> findPlansByTrip(Trip trip);
}
