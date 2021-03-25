package pl.kamfonik.bytheway.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import pl.kamfonik.bytheway.entity.*;
import pl.kamfonik.bytheway.service.interfaces.PlanService;
import pl.kamfonik.bytheway.service.interfaces.TripService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {
    @Mock
    private PlanService planService;

    @Mock
    private TripService tripService;

    @InjectMocks
    private DashboardController dashboardController;

    @Test
    void shouldAddToModel(){
        //given
        Model model = new ConcurrentModel();
        User user = new User();
        List<Category> categories = List.of(new Category(),new Category());
        user.setFavoriteCategories(categories);
        CurrentUser currentUser = new CurrentUser("a","a", new ArrayList<>(),user);
        List<Plan> plans = List.of(new Plan(),new Plan());
        List<Trip> trips = List.of(new Trip(), new Trip());
        when(planService.findUserPlans(user)).thenReturn(plans);
        when(tripService.findUserTrips(user)).thenReturn(trips);

        //when
        dashboardController.dashboard(model,currentUser);

        //then
        assertEquals(plans,model.getAttribute("plans"));
        assertEquals(categories,model.getAttribute("categories"));
        assertEquals(trips,model.getAttribute("trips"));
        assertNotNull(model.getAttribute("plan"));
    }

    @Test
    void shouldRedirectToDashboard(){
        //given
        Model model = new ConcurrentModel();
        User user = new User();
        String expected = "app/dashboard";
        CurrentUser currentUser = new CurrentUser("a","a", new ArrayList<>(),user);

        //when
        String actual = dashboardController.dashboard(model,currentUser);

        //then
        assertEquals(expected,actual);
    }
}