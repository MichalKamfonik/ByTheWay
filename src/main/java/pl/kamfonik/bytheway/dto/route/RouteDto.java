package pl.kamfonik.bytheway.dto.route;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.kamfonik.bytheway.dto.MapDataObject;
import pl.kamfonik.bytheway.dto.Point;
import pl.kamfonik.bytheway.dto.Route;
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
    public MapDataObject getMapDataObject() {

        GeoJsonFeatureCollection geoJsonColl = new GeoJsonFeatureCollection();
        GeoJsonFeature geoRouteJson = new GeoJsonFeature();
        geoRouteJson.setGeometry(new GeoJsonGeometry<>(
                        "LineString",
                        this.legs.stream()
                                .flatMap(leg->leg.getPoints().stream())
                                .map(point -> new Double[]{point.getLongitude(), point.getLatitude()})
                                .toArray(Double[][]::new)
                )
        );
        geoJsonColl.getFeatures().add(geoRouteJson);

        return new MapDataObject(geoJsonColl,getRouteCenter(),getMapZoom());
    }

    @Override
    public Integer getRouteTime() {
        return this.summary.getTravelTimeInSeconds();
    }

    @Override
    public Point getRouteCenter() {
        Double minLat = this.legs.stream()
                .flatMap(leg->leg.getPoints().stream())
                .map(Point::getLatitude)
                .min(Double::compareTo).orElseThrow();
        Double maxLat = this.legs.stream()
                .flatMap(leg->leg.getPoints().stream())
                .map(Point::getLatitude)
                .max(Double::compareTo).orElseThrow();
        Double minLon = this.legs.stream()
                .flatMap(leg->leg.getPoints().stream())
                .map(Point::getLongitude)
                .min(Double::compareTo).orElseThrow();
        Double maxLon = this.legs.stream()
                .flatMap(leg->leg.getPoints().stream())
                .map(Point::getLongitude)
                .max(Double::compareTo).orElseThrow();

        return new PointDto((minLat+maxLat)/2,(minLon+maxLon)/2);
    }

    @Override
    public Integer getMapZoom() {
        int maxMeterPerTile = 40075017;
        if(maxMeterPerTile <= this.summary.getLengthInMeters()){
            return 0;
        }
        for (int i = 1; i < 23; i++) {
            int meterPerTile = maxMeterPerTile / (1<<i);
            if(meterPerTile <= this.summary.getLengthInMeters()){
                return i;
            }
        }
        return 22;
    }
}
