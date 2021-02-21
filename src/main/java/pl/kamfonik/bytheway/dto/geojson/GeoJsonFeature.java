package pl.kamfonik.bytheway.dto.geojson;

import lombok.Data;

@Data
public class GeoJsonFeature {
    private final String type = "Feature";
    private GeoJsonGeometry<?> geometry;
}
