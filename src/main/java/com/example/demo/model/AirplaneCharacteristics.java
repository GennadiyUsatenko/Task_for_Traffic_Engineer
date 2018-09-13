package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "characteristics")
@TypeAlias("characteristic")
public class AirplaneCharacteristics {
    @Id
    private String id;
    @Field(value = "max_speed")
    private Double maxSpeed;
    @Field(value = "max_acceleration")
    private Double maxAcceleration;
    @Field(value = "max_elevation")
    private Double maxElevation;
    @Field(value = "max_degrees")
    private Double maxDegrees;

    public AirplaneCharacteristics() {}

    public AirplaneCharacteristics(Double maxSpeed, Double maxAcceleration, Double maxElevation, Double maxDegrees) {
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAcceleration;
        this.maxElevation = maxElevation;
        this.maxDegrees = maxDegrees;
    }

    public static AirplaneCharacteristics getTestAirplaneCharacteristics(int number){
        switch (number){
            case 1:
                return new AirplaneCharacteristics(10000.0, 40.0, 25.0, 0.5);
            case 2:
                return new AirplaneCharacteristics(10000.0, 40.0, 25.0, 0.5);
            case 3:
                return new AirplaneCharacteristics(10000.0, 40.0, 25.0, 0.5);
            default:
                return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Double getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(Double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public Double getMaxElevation() {
        return maxElevation;
    }

    public void setMaxElevation(Double maxElevation) {
        this.maxElevation = maxElevation;
    }

    public Double getMaxDegrees() {
        return maxDegrees;
    }

    public void setMaxDegrees(Double maxDegrees) {
        this.maxDegrees = maxDegrees;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Max Speed: \t" + maxSpeed + ";\n");
        buffer.append("Max Acceleration: \t" + maxAcceleration + ";\n");
        buffer.append("Max Elevation: \t" + maxElevation + ";\n");
        buffer.append("Max Degrees: \t" + maxDegrees + ";\n");
        return buffer.toString();
    }
}
