package pl.kamfonik.bytheway.entity;

import javax.persistence.*;
import java.time.Duration;
import java.util.Set;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Duration duration;
    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Place origin;
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Place destination;
    @OneToMany
    private Set<Activity> activities;
}
