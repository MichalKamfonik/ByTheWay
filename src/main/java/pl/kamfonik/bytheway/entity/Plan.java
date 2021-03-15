package pl.kamfonik.bytheway.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.kamfonik.bytheway.validator.UserFormValidation;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.groups.Default;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "plans")
@Data
@JsonSerialize(using = LocalDateTimeSerializer.class)
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = {UserFormValidation.class})
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Null(groups = {UserFormValidation.class})
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @NotNull(groups = {UserFormValidation.class, Default.class})
    private @Valid Trip trip;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @NotNull(groups = {UserFormValidation.class, Default.class})
    @Future(groups = {UserFormValidation.class, Default.class})
    private LocalDate startTime;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Null(groups = {UserFormValidation.class})
    private LocalDate endTime;

    public String json() {
        return "{ id: '" + id
                + "', trip: '" + trip.getName()
                + "', startTime: '" + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "', endTime: '" + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "' }";
    }
}
