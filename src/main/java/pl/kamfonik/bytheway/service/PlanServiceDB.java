package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.entity.Plan;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.repository.PlanRepository;
import pl.kamfonik.bytheway.service.interfaces.PlanService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlanServiceDB implements PlanService {
    private final PlanRepository planRepository;

    public List<Plan> findUserPlans(User user){
        return planRepository.findByUserOrderByStartTimeAsc(user);
    }

    public List<Plan> findAllPlans(){
        return planRepository.findAll();
    }

    public Optional<Plan> save(Plan plan){
        try {
            return Optional.of(planRepository.save(plan));
        } catch (IllegalArgumentException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Plan> findPlanById(Long id) {
        return planRepository.findById(id);
    }

    @Override
    public Boolean checkUserPlan(Long id, User user) {
        return findPlanById(id)
                .map(Plan::getUser)
                .map(User::getId)
                .map(userId->userId.equals(user.getId()))
                .orElse(false);
    }

    @Override
    public void delete(Long id) {
        planRepository.deleteById(id);
    }

    @Override
    public List<Plan> findPlansByTrip(Trip trip) {
        return planRepository.findByTrip(trip);
    }
}
