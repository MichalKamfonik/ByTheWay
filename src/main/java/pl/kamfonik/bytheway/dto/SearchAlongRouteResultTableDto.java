package pl.kamfonik.bytheway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchAlongRouteResultTableDto {
    List<SearchResultDto> results;
}