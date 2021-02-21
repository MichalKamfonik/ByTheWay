package pl.kamfonik.bytheway.dto;

public interface Route {
    RouteObjectForMapping getRouteObjectForMapping();
    Integer getRouteTime();
    Point getRouteCenter();
    Integer getMapZoom();
}
