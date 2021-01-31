package pl.kamfonik.bytheway.entity;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String name;
    private String description;
}
