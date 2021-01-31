package pl.kamfonik.bytheway.entity;

import pl.kamfonik.bytheway.entity.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @ManyToMany
    private List<Category> categories;
}
