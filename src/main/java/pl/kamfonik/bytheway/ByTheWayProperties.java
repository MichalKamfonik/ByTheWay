package pl.kamfonik.bytheway;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
@ConfigurationProperties("bytheway")
public class ByTheWayProperties {
    private final Category category = new Category();
    @Getter
    @Setter
    @ToString
    public static class Category {
        private String apikey;
    }
}
