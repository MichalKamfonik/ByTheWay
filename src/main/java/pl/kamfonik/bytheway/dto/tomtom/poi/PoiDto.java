package pl.kamfonik.bytheway.dto.tomtom.poi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoiDto {
    private String name;
    private Set<PoiCategoryDto> categorySet;
}
