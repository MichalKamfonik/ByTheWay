package pl.kamfonik.bytheway.dto;

public interface Route {
    MapDataObject getMapDataObject();
    Integer getRouteTime();
    Point getRouteCenter();
    Integer getMapZoom();
}
