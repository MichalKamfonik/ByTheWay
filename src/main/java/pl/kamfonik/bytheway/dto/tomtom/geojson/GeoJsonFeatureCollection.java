package pl.kamfonik.bytheway.dto.tomtom.geojson;

import lombok.Data;
import pl.kamfonik.bytheway.dto.RouteObjectForMapping;

import java.util.ArrayList;
import java.util.List;

@Data
public class GeoJsonFeatureCollection implements RouteObjectForMapping {
    private final String type = "FeatureCollection";
    private List<GeoJsonFeature> features;

    public GeoJsonFeatureCollection() {
        features = new ArrayList<>();
    }
}
