package pl.kamfonik.bytheway.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.kamfonik.bytheway.validator.UserFormValidation;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "[._]?(([a-zA-Z][a-zA-Z0-9]*)|([a-zA-Z0-9]*[a-zA-Z]))([._]?[a-zA-Z0-9])*",
            message = "Username has to contain at least one letter. It can contain \"_\" and \".\" but it must be followed by a letter or digit.")
    @Column(nullable = false, unique = true, length = 20)
    private String username;


    @Column(nullable = false, length = 60)
    private String password;

    private int enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @Size(max = 10, groups={UserFormValidation.class, Default.class})
    @JoinTable(name = "user_category", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<@Valid Category> favoriteCategories;
}
