package pl.kamfonik.bytheway.dto.route;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.kamfonik.bytheway.dto.Route;
import pl.kamfonik.bytheway.dto.RouteObjectForMapping;
import pl.kamfonik.bytheway.dto.geojson.GeoJsonFeature;
import pl.kamfonik.bytheway.dto.geojson.GeoJsonFeatureCollection;
import pl.kamfonik.bytheway.dto.geojson.GeoJsonGeometry;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteDto implements Route {
    private RouteSummaryDto summary;
    private List<LegDto> legs;

    @Override
    public RouteObjectForMapping getRouteObjectForMapping() {

        GeoJsonFeatureCollection geoJsonColl = new GeoJsonFeatureCollection();
        GeoJsonFeature geoRouteJson = new GeoJsonFeature();
        geoRouteJson.setGeometry(new GeoJsonGeometry<>(
                        GeoJsonGeometry.LINE,
                        this.legs.stream()
                                .flatMap(leg->leg.getPoints().stream())
                                .map(point -> new Double[]{point.getLongitude(), point.getLatitude()})
                                .toArray(Double[][]::new)
                )
        );
        geoJsonColl.getFeatures().add(geoRouteJson);

        return geoJsonColl;
    }

    @Override
    public Integer getRouteTime() {
        return this.summary.getTravelTimeInSeconds();
    }
}
