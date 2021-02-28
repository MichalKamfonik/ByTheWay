package pl.kamfonik.bytheway.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import pl.kamfonik.bytheway.validator.PlaceID;
import pl.kamfonik.bytheway.validator.PlacesValidationGroup;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "places")
@Data
public class Place {
    @Id
    @NotNull
    @PlaceID(message = "Place's ID is not correct",groups = {PlacesValidationGroup.class})
    private String id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String address;

    @Column(nullable = false)
    @NotNull
    @Range(min = -90L, max = 90L)
    private Double lat;

    @Column(nullable = false)
    @NotNull
    @Range(min = -180L, max = 180L)
    private Double lon;

    @ManyToMany
    @NotNull
    @Size(min = 0)
    private List<@Valid Category> categories;

    @Transient
    private Integer detourOffset;
}
