package pl.kamfonik.bytheway.dto.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDto {
    private String id;
    private String name;
    private String Address;
    private Double lon;
    private Double lat;
    private Set<CategoryDto> categories;
    private Integer number;
}
