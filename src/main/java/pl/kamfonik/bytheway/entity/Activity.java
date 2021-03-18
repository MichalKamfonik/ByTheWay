package pl.kamfonik.bytheway.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.kamfonik.bytheway.validator.UserFormValidation;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.time.LocalTime;

@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = {UserFormValidation.class})
    private Long id;

    @Size(max = 255, groups = {UserFormValidation.class, Default.class})
    private String description;

    @ManyToOne
    @JoinColumn(name = "place_id")
    @NotNull(groups = {UserFormValidation.class, Default.class})
    private @Valid Place place;

    @NotNull(groups = {UserFormValidation.class, Default.class})
    @PositiveOrZero(groups = {UserFormValidation.class, Default.class})
    @Column(nullable = false)
    private Integer duration;

    @NotNull(groups = {UserFormValidation.class, Default.class})
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime arrival;

    @NotNull(groups = {UserFormValidation.class, Default.class})
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime departure;

    @NotNull(groups = {UserFormValidation.class, Default.class})
    @PositiveOrZero(groups = {UserFormValidation.class, Default.class})
    @Column(nullable = false)
    private Integer number;

    public Activity(String description, Place place, Integer duration, LocalTime arrival, LocalTime departure, Integer number) {
        this.description = description;
        this.place = place;
        this.duration = duration;
        this.arrival = arrival;
        this.departure = departure;
        this.number = number;
    }
}
