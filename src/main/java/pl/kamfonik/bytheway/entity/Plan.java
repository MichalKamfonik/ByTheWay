package pl.kamfonik.bytheway.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "plans")
@Data
@JsonSerialize(using = LocalDateTimeSerializer.class)
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate startTime;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate endTime;

    public String json() {
        return "{ id: '" + id
                + "', trip: '" + trip.getName()
                + "', startTime: '" + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "', endTime: '" + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "' }";
    }
}
