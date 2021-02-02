package pl.kamfonik.bytheway.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime arrival;
    private LocalDateTime departure;
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}
