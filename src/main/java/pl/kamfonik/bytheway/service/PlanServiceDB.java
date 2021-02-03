package pl.kamfonik.bytheway.service;

import org.springframework.stereotype.Service;
import pl.kamfonik.bytheway.entity.Plan;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.repository.PlanRepository;

import java.util.List;

@Service
public class PlanServiceDB implements PlanService{
    private final PlanRepository planRepository;

    public PlanServiceDB(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> findUserPlans(User user){
        return planRepository.findByUserOrderByStartAsc(user);
    }

    public List<Plan> findAllPlans(){
        return planRepository.findAll();
    }
}
