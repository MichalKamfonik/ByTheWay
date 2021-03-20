package pl.kamfonik.bytheway.dto.tomtom.route;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutesTableDto {
    List<RouteDto> routes;
}
