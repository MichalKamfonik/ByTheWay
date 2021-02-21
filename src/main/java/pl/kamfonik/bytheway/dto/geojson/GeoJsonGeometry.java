package pl.kamfonik.bytheway.dto.geojson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoJsonGeometry<T> {
    public static final String LINE = "LineString";
    public static final String POINT = "Point";
    private String type;
    private T[] coordinates;
}
