package pl.kamfonik.bytheway.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kamfonik.bytheway.ByTheWayProperties;
import pl.kamfonik.bytheway.dto.category.CategoriesTableDto;
import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.repository.CategoryRepository;
import pl.kamfonik.bytheway.service.interfaces.CategoryService;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Setter
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceTomTomDB implements CategoryService {
    private static long MAIN_CATEGORIES_UPPER_BOUNDARY = 9999L;

    private final CategoryRepository categoryRepository;
    private final ByTheWayProperties byTheWayProperties;
    private static final String TOMTOM_CATEGORIES_API_URL =
            "https://api.tomtom.com/search/2/poiCategories.json?key=";

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
        Objects.requireNonNull(forEntity.getBody()).getCategories().stream()
                .filter(categoryDto -> categoryDto.getId()< MAIN_CATEGORIES_UPPER_BOUNDARY /*only main categories*/)
                .map(categoryDto -> {
                    Category category = new Category();
                    category.setId(categoryDto.getId());
                    category.setName(categoryDto.getName());
                    return category;
                })
                .forEach(categoryRepository::save);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findCategoriesWithIds(Collection<Long> ids) {
        return categoryRepository.findAllById(ids);
    }
}
