package pl.kamfonik.bytheway.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Place place;
    private Integer duration;
    private LocalTime arrival;
    private LocalTime departure;

    public Activity(String description, Place place, Integer duration, LocalTime arrival, LocalTime departure) {
        this.description = description;
        this.place = place;
        this.duration = duration;
        this.arrival = arrival;
        this.departure = departure;
    }
}
