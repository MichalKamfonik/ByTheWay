package pl.kamfonik.bytheway.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.kamfonik.bytheway.validator.UserCategoriesValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
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

    @Transient
    @NotBlank
    @Length(min = 4, max = 16)
    @Pattern(regexp = ".*[a-z].*", message = "Password has to contain at least one lower case letter")
    @Pattern(regexp = ".*[A-Z].*", message = "Password has to contain at least one capital letter")
    @Pattern(regexp = ".*[0-9].*", message = "Password has to contain at least one digit")
    @Pattern(regexp = ".*[!@#$%^&*()\\-+=_].*", message = "Password has to contain at least one special character (a-zA-Z0-9!@#$%^&*()-+=_)")
    private String initialPassword;

    @Transient
    @NotBlank
    private String repeatedPassword;

    private int enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @Size(max = 10, groups={UserCategoriesValidationGroup.class, Default.class})
    @JoinTable(name = "user_category", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> favoriteCategories;

    public boolean isAdmin(){
        return roles.stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));
    }
}
