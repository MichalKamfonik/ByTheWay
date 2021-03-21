package pl.kamfonik.bytheway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import pl.kamfonik.bytheway.controller.*;
import pl.kamfonik.bytheway.converter.*;
import pl.kamfonik.bytheway.repository.*;
import pl.kamfonik.bytheway.service.interfaces.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-tests.properties")
public class SmokeTest {

    // Controllers
    @Autowired private AdminController adminController;
    @Autowired private CategoryController categoryController;
    @Autowired private DashboardController dashboardController;
    @Autowired private PlaceRestController placeRestController;
    @Autowired private PlanController planController;
    @Autowired private RegisterController registerController;
    @Autowired private RouteRestController routeRestController;
    @Autowired private TripController tripController;

    //Converters
    @Autowired private CategoryConverter categoryConverter;
    @Autowired private CategoryEntity2DtoConverter categoryEntity2DtoConverter;
    @Autowired private PlaceConverter placeConverter;
    @Autowired private PlaceEntity2DtoConverter placeEntity2DtoConverter;
    @Autowired private PlacesListEntity2DtoConverter placesListEntity2DtoConverter;

    //Repositories
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private PlaceRepository placeRepository;
    @Autowired private PlanRepository planRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private TripRepository tripRepository;
    @Autowired private UserRepository userRepository;

    //Services
    @Autowired private CategoryService categoryService;
    @Autowired private PlaceService placeService;
    @Autowired private PlanService planService;
    @Autowired private RouteService routeService;
    @Autowired private TripService tripService;
    @Autowired private UserService userService;

    @Test
    public void contextLoads() {
        assertThat(adminController).isNotNull();
        assertThat(categoryController).isNotNull();
        assertThat(dashboardController).isNotNull();
        assertThat(placeRestController).isNotNull();
        assertThat(planController).isNotNull();
        assertThat(registerController).isNotNull();
        assertThat(routeRestController).isNotNull();
        assertThat(tripController).isNotNull();
        assertThat(categoryConverter).isNotNull();
        assertThat(categoryEntity2DtoConverter).isNotNull();
        assertThat(placeConverter).isNotNull();
        assertThat(placeEntity2DtoConverter).isNotNull();
        assertThat(placesListEntity2DtoConverter).isNotNull();
        assertThat(categoryRepository).isNotNull();
        assertThat(placeRepository).isNotNull();
        assertThat(planRepository).isNotNull();
        assertThat(roleRepository).isNotNull();
        assertThat(tripRepository).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(categoryService).isNotNull();
        assertThat(placeService).isNotNull();
        assertThat(planService).isNotNull();
        assertThat(routeService).isNotNull();
        assertThat(tripService).isNotNull();
        assertThat(userService).isNotNull();
    }
}
