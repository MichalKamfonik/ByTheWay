package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.entity.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    void initialize();
    Category findById(Long id);
    List<Category> findCategoriesWithIds(Collection<Long> ids);
}
