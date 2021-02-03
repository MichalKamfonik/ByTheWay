package pl.kamfonik.bytheway.service;

import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.entity.Category;

import java.util.Set;

@Service
@Setter
public class CategoryServiceTomTomDB implements CategoryService {

    private final ByTheWayProperties byTheWayProperties;

    public CategoryServiceTomTomDB(ByTheWayProperties byTheWayProperties) {
        this.byTheWayProperties = byTheWayProperties;
    }

    @Override
    public Set<Category> findAllCategories() {
        return null;
    }

    @Override
    public Set<Category> initialize() {
//        new RestTemplate()
        return null;
    }
}
