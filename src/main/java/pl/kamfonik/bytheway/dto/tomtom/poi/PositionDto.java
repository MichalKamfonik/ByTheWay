package pl.kamfonik.bytheway.dto.tomtom.poi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.kamfonik.bytheway.dto.Point;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionDto implements Point {
    private Double lat;
    private Double lon;

    @Override
    public Double getLatitude() {
        return getLat();
    }
    @Override
    public Double getLongitude() {
        return getLon();
    }
}
