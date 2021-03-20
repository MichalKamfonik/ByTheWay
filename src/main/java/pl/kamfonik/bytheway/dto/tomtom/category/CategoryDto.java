package pl.kamfonik.bytheway.dto.tomtom.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {
    private Long id;
    private String name;
    @JsonProperty("childCategoryIds")
    private List<Long> childCategories;
}
