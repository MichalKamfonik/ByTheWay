package pl.kamfonik.bytheway.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 60)
    private String username;
    private String password;
    @Transient
    private String repeatedPassword;
    private int enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @ManyToMany(fetch = FetchType.EAGER)
    @Size(max = 10)
    @JoinTable(name = "user_category", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> favoriteCategories;
    public boolean isAdmin(){
        return roles.stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));
    }
}
