package pl.kamfonik.bytheway.dto.tomtom.poi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultTableDto {
    private List<SearchResultDto> results;
}
