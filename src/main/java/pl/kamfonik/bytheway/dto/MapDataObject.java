package pl.kamfonik.bytheway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapDataObject {
    private RouteObjectForMapping geoJson;
    private Point routeCenter;
    private Integer mapZoom;
}
