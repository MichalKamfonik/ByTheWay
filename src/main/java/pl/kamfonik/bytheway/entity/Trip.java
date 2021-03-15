package pl.kamfonik.bytheway.entity;

import lombok.Data;
import pl.kamfonik.bytheway.validator.ActivitiesList;
import pl.kamfonik.bytheway.validator.TripID;
import pl.kamfonik.bytheway.validator.UserFormValidation;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "trips")
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TripID(message = "Trip's ID is not correct", groups = {UserFormValidation.class})
    private Long id;

    @NotNull(groups = {UserFormValidation.class, Default.class})
    @Positive(groups = {UserFormValidation.class, Default.class})
    private Integer duration;

    @NotNull(groups = {UserFormValidation.class, Default.class})
    @Size(max = 255, groups = {UserFormValidation.class, Default.class})
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("number ASC")
    @NotNull(groups = {UserFormValidation.class, Default.class})
    @Size(min = 3,groups = {UserFormValidation.class, Default.class})
    @ActivitiesList(groups = {UserFormValidation.class, Default.class})
    private List<@Valid Activity> activities;

    @NotNull(groups = {UserFormValidation.class, Default.class})
    private LocalTime departure;

    @NotNull(groups = {UserFormValidation.class, Default.class})
    private LocalTime arrival;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
