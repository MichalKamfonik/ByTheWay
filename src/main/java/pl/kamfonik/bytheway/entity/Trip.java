package pl.kamfonik.bytheway.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;

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
    @OneToMany()
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Activity> activities;
    private LocalTime departure;
    private LocalTime arrival;
}
