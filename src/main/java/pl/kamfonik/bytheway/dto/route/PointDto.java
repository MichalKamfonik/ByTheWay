package pl.kamfonik.bytheway.dto.route;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamfonik.bytheway.dto.Point;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class PointDto implements Point {
    private Double latitude;
    private Double longitude;
}
