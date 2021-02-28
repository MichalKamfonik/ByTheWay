package pl.kamfonik.bytheway.entity;

import lombok.Data;
import pl.kamfonik.bytheway.validator.CategoryID;
import pl.kamfonik.bytheway.validator.UserFormValidation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @NotNull
    @CategoryID(message = "Category's ID is not correct", groups={UserFormValidation.class})
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(max = 255)
    @NotBlank(groups={UserFormValidation.class, Default.class})
    private String name;
}
