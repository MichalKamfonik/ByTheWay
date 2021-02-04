package pl.kamfonik.bytheway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultDto {
    private String id;
    @JsonProperty("poi")
    private PoiDto poiDto;
    @JsonProperty("address")
    private AddressDto addressDto;
    @JsonProperty("position")
    private PositionDto positionDto;
}
