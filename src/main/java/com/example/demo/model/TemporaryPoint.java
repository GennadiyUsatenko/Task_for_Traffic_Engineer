package com.example.demo.model;

import org.gavaghan.geodesy.GlobalCoordinates;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;

@Document(collection = "temporary_points")
@TypeAlias("tpoint")
public class TemporaryPoint extends GlobalPosition {
    @Id
    String id;
    @Field(value = "speed")
    private Double speed;
    @Field(value = "alpha")
    private Double courseInDegrees;

    public TemporaryPoint(){
        super(0d, 0d, 0d);
    }

    public TemporaryPoint(double latitude, double longitude, double elevation, double speed, double courseInDegrees){
        super(latitude, longitude, elevation);
        this.speed = speed;
        this.courseInDegrees = courseInDegrees;
    }

    public TemporaryPoint(GlobalPosition position, double speed, double courseInDegrees) {
        this(position.getLatitude(), position.getLongitude(), position.getElevation(), speed, courseInDegrees);
    }

    public GlobalCoordinates getGlobalCoordinate(){
        return new GlobalCoordinates(getLatitude(), getLongitude());
    }

    public GlobalPosition getGlobalPosition(){
        return new GlobalPosition(getLatitude(), getLongitude(), getElevation());
    }

    public Double getCourseInDegrees() {
        return courseInDegrees;
    }

    public void setCourseInDegrees(Double courseInDegrees) {
        this.courseInDegrees = courseInDegrees;
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
        buffer.append(";courseInDegrees=");
        buffer.append(Double.toString(this.courseInDegrees));
        buffer.append(";speed=");
        buffer.append(Double.toString(this.speed));
        return buffer.toString();
    }

}
