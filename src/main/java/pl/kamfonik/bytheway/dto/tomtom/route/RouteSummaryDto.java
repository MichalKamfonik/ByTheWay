package pl.kamfonik.bytheway.dto.tomtom.route;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteSummaryDto {
    private Integer lengthInMeters;
    private Integer travelTimeInSeconds;
}
