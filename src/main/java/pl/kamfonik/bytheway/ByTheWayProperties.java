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
    private final SearchPOI searchPOI = new SearchPOI();
    private final Routing routing = new Routing();
    @Getter
    @Setter
    @ToString
    public static class Category {
        private String apikey;
    }
    @Getter
    @Setter
    @ToString
    public static class SearchPOI {
        private String apikey;
    }
    @Getter
    @Setter
    @ToString
    public static class Routing {
        private String apikey;
    }
}
