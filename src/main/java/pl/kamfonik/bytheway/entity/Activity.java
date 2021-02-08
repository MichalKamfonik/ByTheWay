package pl.kamfonik.bytheway.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "activities")
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
    private Integer duration;
}
