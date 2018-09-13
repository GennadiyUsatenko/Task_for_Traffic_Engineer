package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "airplanes")
@TypeAlias("airplane")
public class Airplane {
    @Id
    String id;
    @DBRef
    AirplaneCharacteristics airplaneCharacteristics;
    @DBRef
    TemporaryPoint position;
    @DBRef
    Flight flight;

    public Airplane() {}

    public static Airplane getTestAirplane(int number){
        switch (number){
            case 1:
                return new Airplane(
                        AirplaneCharacteristics.getTestAirplaneCharacteristics(number), Flight.getTestFlight(number));
            case 2:
                return new Airplane(
                        AirplaneCharacteristics.getTestAirplaneCharacteristics(number), Flight.getTestFlight(number));
            case 3:
                return new Airplane(
                        AirplaneCharacteristics.getTestAirplaneCharacteristics(number), Flight.getTestFlight(number));
            default:
                return null;
        }
    }

    public Airplane(AirplaneCharacteristics airplaneCharacteristics, Flight flight){
        this.airplaneCharacteristics = airplaneCharacteristics;
        this.flight = flight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AirplaneCharacteristics getAirplaneCharacteristics() {
        return airplaneCharacteristics;
    }

    public void setAirplaneCharacteristics(AirplaneCharacteristics airplaneCharacteristics) {
        this.airplaneCharacteristics = airplaneCharacteristics;
    }

    public TemporaryPoint getPosition() {
        return position;
    }

    public void setPosition(TemporaryPoint position) {
        this.position = position;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n------------------------------------------------------------------------------------------\n");
        buffer.append("The airplane with characteristics:\n");
        buffer.append(airplaneCharacteristics.toString());
        buffer.append("Starts flight by WayPoints:\n");
        buffer.append(flight.toString());
        buffer.append("\n------------------------------------------------------------------------------------------\n");
        return buffer.toString();
    }
}
