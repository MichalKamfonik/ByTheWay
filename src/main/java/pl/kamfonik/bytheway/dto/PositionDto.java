package pl.kamfonik.bytheway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionDto {
    private Double lat;
    private Double lon;
}
