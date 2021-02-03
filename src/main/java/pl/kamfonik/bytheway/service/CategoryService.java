package pl.kamfonik.bytheway.service;

import pl.kamfonik.bytheway.entity.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> findAllCategories();
    Set<Category> initialize();
}
