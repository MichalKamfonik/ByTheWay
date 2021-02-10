package pl.kamfonik.bytheway.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "trips")
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer duration;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Activity> activities;
    private LocalTime departure;
    private LocalTime arrival;
}
