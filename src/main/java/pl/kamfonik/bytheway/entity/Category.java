package pl.kamfonik.bytheway.entity;

import lombok.Data;
import pl.kamfonik.bytheway.validator.CategoryID;
import pl.kamfonik.bytheway.validator.CategoriesValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @CategoryID(message = "Category's ID is not correct", groups={CategoriesValidationGroup.class})
    private Long id;
    @Column(nullable = false, unique = true)
    @NotBlank(groups={CategoriesValidationGroup.class, Default.class})
    private String name;
}
