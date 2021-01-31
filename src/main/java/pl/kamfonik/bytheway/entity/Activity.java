package pl.kamfonik.bytheway.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
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
