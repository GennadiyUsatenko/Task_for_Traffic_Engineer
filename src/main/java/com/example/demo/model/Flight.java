package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "flights")
@TypeAlias("flight")
public class Flight {
    @Id
    private String number;
    @DBRef
    private List<WayPoint> wayPoints;
    @DBRef
    private List<TemporaryPoint> passedPoints = new ArrayList<>();

    public Flight() {}

    public Flight(List<WayPoint> wayPoints){
        this.wayPoints = wayPoints;
    }

    public static Flight getTestFlight(int number){
        switch (number){
            case 1:
                return new Flight(WayPoint.getTestWayPoints(number));
            case 2:
                return new Flight(WayPoint.getTestWayPoints(number));
            case 3:
                return new Flight(WayPoint.getTestWayPoints(number));
            default:
                return null;
        }
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<WayPoint> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public List<TemporaryPoint> getPassedPoints() {
        return passedPoints;
    }

    public void setPassedPoints(List<TemporaryPoint> passedPoints) {
        this.passedPoints = passedPoints;
    }

    public String getFlightInfo(){
        return "Flight number: " + number + "; Duration of flight: " + passedPoints.size() + " sec.;";
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        wayPoints.forEach(w -> buffer.append(w.toString() + "\n"));
        return buffer.toString();
    }
}
