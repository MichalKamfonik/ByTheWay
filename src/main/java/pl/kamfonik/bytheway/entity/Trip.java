package pl.kamfonik.bytheway.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "trips")
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer duration;
    private String name;
    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Place origin;
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Place destination;
    @OneToMany
    private Set<Activity> activities;
    private LocalTime departure;
    private LocalTime arrival;
}
