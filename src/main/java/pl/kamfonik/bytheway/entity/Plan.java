package pl.kamfonik.bytheway.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "plans")
@Data
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
}
