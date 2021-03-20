package pl.kamfonik.bytheway.dto.tomtom.poi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultDto {
    private String id;
    private PoiDto poi;
    private AddressDto address;
    private PositionDto position;
}
