package pl.kamfonik.bytheway.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
    private Integer duration;
    private LocalTime arrival;
    private LocalTime departure;
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
