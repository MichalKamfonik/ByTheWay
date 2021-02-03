package pl.kamfonik.bytheway.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
}
