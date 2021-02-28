package pl.kamfonik.bytheway.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import pl.kamfonik.bytheway.validator.UserFormValidation;
import pl.kamfonik.bytheway.validator.PlaceID;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.util.List;

@Entity
@Table(name = "places")
@Data
public class Place {
    @Id
    @NotNull(groups = {UserFormValidation.class, Default.class})
    @PlaceID(message = "Place's ID is not correct", groups = {UserFormValidation.class})
    private String id;

    @Column(nullable = false)
    @NotBlank(groups = {UserFormValidation.class, Default.class})
    @Size(max = 255, groups = {UserFormValidation.class, Default.class})
    private String name;

    @Column(nullable = false)
    @NotBlank(groups = {UserFormValidation.class, Default.class})
    @Size(max = 255, groups = {UserFormValidation.class, Default.class})
    private String address;

    @Column(nullable = false)
    @NotNull(groups = {UserFormValidation.class, Default.class})
    @Range(min = -90L, max = 90L, groups = {UserFormValidation.class, Default.class})
    private Double lat;

    @Column(nullable = false)
    @NotNull(groups = {UserFormValidation.class, Default.class})
    @Range(min = -180L, max = 180L, groups = {UserFormValidation.class, Default.class})
    private Double lon;

    @ManyToMany
    @NotNull(groups = {UserFormValidation.class, Default.class})
    @Size(groups = {UserFormValidation.class, Default.class})
    private List<@Valid Category> categories;

    @Transient
    private Integer detourOffset;
}
