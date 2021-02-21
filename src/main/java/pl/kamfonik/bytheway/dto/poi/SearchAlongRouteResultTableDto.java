package pl.kamfonik.bytheway.dto.poi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchAlongRouteResultTableDto {
    private List<SearchAlongRouteResultDto> results;
}
