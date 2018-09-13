package com.example.demo.model;

import org.gavaghan.geodesy.GlobalCoordinates;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.List;

@Document(collection = "way_points")
@TypeAlias("wpoint")
public class WayPoint extends GlobalPosition {
    @Id
    String id;
    @Field(value = "speed")
    private Double speed;

    public WayPoint(){
        super(0d, 0d, 0d);
    }

    public WayPoint(double latitude, double longitude, double elevation, double speed){
        super(latitude, longitude, elevation);
        this.speed = speed;
    }

    public WayPoint(GlobalPosition position, double speed) {
        this(position.getLatitude(), position.getLongitude(), position.getElevation(), speed);
    }

    public static List<WayPoint> getTestWayPoints(int number){
        switch (number){
            case 1:
                return Arrays.asList(
                        new WayPoint(38.88922, -77.04978, 5000.0, 2000.0),
                        new WayPoint(43.85889, -74.29583, 2000.0, 3800.0),
                        new WayPoint(48.85889, -69.29583, 0.0, 0.0)
                );
            case 2:
                return Arrays.asList(
                        new WayPoint(38.88922, -77.04978, 5000.0, 2000.0),
                        new WayPoint(43.85889, -74.29583, 2000.0, 3800.0),
                        new WayPoint(48.85889, -69.29583, 0.0, 0.0)
                );
            case 3:
                return Arrays.asList(
                        new WayPoint(38.88922, -77.04978, 5000.0, 2000.0),
                        new WayPoint(43.85889, -74.29583, 2000.0, 3800.0),
                        new WayPoint(48.85889, -69.29583, 0.0, 0.0)
                );
            default:
                return null;
        }
    }

    public GlobalCoordinates getGlobalCoordinate(){
        return new GlobalCoordinates(getLatitude(), getLongitude());
    }

    public GlobalPosition getGlobalPosition(){
        return new GlobalPosition(getLatitude(), getLongitude(), getElevation());
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(super.toString());
        buffer.append(";speed=");
        buffer.append(Double.toString(this.speed));
        return buffer.toString();
    }
}
