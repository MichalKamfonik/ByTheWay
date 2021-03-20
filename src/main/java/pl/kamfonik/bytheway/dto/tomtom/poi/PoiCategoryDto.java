package pl.kamfonik.bytheway.dto.tomtom.poi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoiCategoryDto {
    private Long id;
}
