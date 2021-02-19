package pl.kamfonik.bytheway.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "places")
@Data
public class Place {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Double lat;
    @Column(nullable = false)
    private Double lon;
    @ManyToMany
    private List<Category> categories;
    @Transient
    private Integer detourOffset;
}
