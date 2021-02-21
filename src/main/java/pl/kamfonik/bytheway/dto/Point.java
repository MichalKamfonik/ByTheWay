package pl.kamfonik.bytheway.dto;

public interface Point {
    Double getLatitude();
    Double getLongitude();
    default String getPosition(){
        return "["+getLongitude()+","+getLatitude()+"]";
    }
}
