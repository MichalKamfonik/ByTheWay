package pl.kamfonik.bytheway.service.interfaces;

import pl.kamfonik.bytheway.entity.Category;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAllCategories();

    void initialize();

    Optional<Category> findById(Long id);

    List<Category> findCategoriesWithIds(Collection<Long> ids);
}
