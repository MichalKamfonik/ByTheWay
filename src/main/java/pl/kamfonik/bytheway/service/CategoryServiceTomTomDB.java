package pl.kamfonik.bytheway.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.dto.CategoriesTableDto;
import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Setter
@Slf4j
public class CategoryServiceTomTomDB implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ByTheWayProperties byTheWayProperties;
    private static final String TOMTOM_CATEGORIES_API_URL =
            "https://api.tomtom.com/search/2/poiCategories.json?key=";

    public CategoryServiceTomTomDB(CategoryRepository categoryRepository, ByTheWayProperties byTheWayProperties) {
        this.categoryRepository = categoryRepository;
        this.byTheWayProperties = byTheWayProperties;
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public void initialize() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CategoriesTableDto> forEntity = restTemplate.getForEntity(
                TOMTOM_CATEGORIES_API_URL + byTheWayProperties.getCategory().getApikey(),
                CategoriesTableDto.class);
        forEntity.getBody().getCategories().stream()
                .filter(categoryDto -> categoryDto.getId()<9999L /*only main categories*/)
                .map(categoryDto -> {
                    Category category = new Category();
                    category.setId(categoryDto.getId());
                    category.setName(categoryDto.getName());
                    log.debug(category.getName());
                    return category;
                })
                .forEach(categoryRepository::save);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }
}
