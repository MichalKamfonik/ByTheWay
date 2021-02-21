package pl.kamfonik.bytheway.dto;

public interface Point {
    Double getLatitude();
    Double getLongitude();
    default String getPositionString(){
        return "["+getLongitude()+","+getLatitude()+"]";
    }
    default Double[] getPosition(){
        return new Double[]{getLongitude(),getLatitude()};
    }
}
