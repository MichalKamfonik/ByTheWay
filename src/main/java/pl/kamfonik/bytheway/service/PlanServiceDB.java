package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.entity.Plan;
import pl.kamfonik.bytheway.entity.Trip;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.repository.PlanRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanServiceDB implements PlanService{
    private final PlanRepository planRepository;

    public List<Plan> findUserPlans(User user){
        return planRepository.findByUserOrderByStartTimeAsc(user);
    }

    public List<Plan> findAllPlans(){
        return planRepository.findAll();
    }

    public Plan save(Plan plan){
        return planRepository.save(plan);
    }

    @Override
    public Plan findPlanById(Long id) {
        return planRepository.findById(id).orElseThrow();
    }

    @Override
    public Boolean checkUserPlan(Long id, User user) {
        return findPlanById(id).getUser().equals(user);
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
