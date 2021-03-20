package pl.kamfonik.bytheway.dto.app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "[._]?(([a-zA-Z][a-zA-Z0-9]*)|([a-zA-Z0-9]*[a-zA-Z]))([._]?[a-zA-Z0-9])*",
            message = "Username has to contain at least one letter. It can contain \"_\" and \".\" but it must be followed by a letter or digit.")
    private String username;

    @NotBlank
    @Length(min = 4, max = 16)
    @Pattern(regexp = ".*[a-z].*", message = "Password has to contain at least one lower case letter")
    @Pattern(regexp = ".*[A-Z].*", message = "Password has to contain at least one capital letter")
    @Pattern(regexp = ".*[0-9].*", message = "Password has to contain at least one digit")
    @Pattern(regexp = ".*[!@#$%^&*()\\-+=_].*", message = "Password has to contain at least one special character (a-zA-Z0-9!@#$%^&*()-+=_)")
    private String password;

    @Transient
    @NotBlank
    private String repeatedPassword;
}
