package pl.kamfonik.bytheway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchAlongRouteResultDto extends SearchResultDto{
    private Integer detourTime;
    private Integer detourDistance;
    private Integer detourOffset;
}
