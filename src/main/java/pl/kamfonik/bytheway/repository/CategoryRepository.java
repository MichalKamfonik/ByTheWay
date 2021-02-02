package pl.kamfonik.bytheway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamfonik.bytheway.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
