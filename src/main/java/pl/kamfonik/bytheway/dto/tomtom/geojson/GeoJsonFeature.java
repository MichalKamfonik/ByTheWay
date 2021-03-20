package pl.kamfonik.bytheway.dto.tomtom.geojson;

import lombok.Data;

@Data
public class GeoJsonFeature {
    private final String type = "Feature";
    private GeoJsonGeometry<?> geometry;
}
