package com.example.demo.service;

import com.example.demo.model.AirplaneCharacteristics;
import com.example.demo.model.TemporaryPoint;
import com.example.demo.model.WayPoint;
import org.gavaghan.geodesy.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gennadiy Usatenko
 * @version 11.09.2018
 */
@Service
public class PlaneCalculation {
    // final TemporaryPoints which will be returned from calculateRoute method
    private List<TemporaryPoint> finalPoints;
    // AirplaneCharacteristics
    private AirplaneCharacteristics characteristics;
    // geo calculator
    private GeodeticCalculator geoCalc;
    // reference elllipsoid
    private Ellipsoid reference;
    // starting geo position(Latitude, Longitude, Height) between two WayPoints
    private GlobalPosition Position_Start;
    // container for determining end angle between two TemporaryPoints
    private double[] endBearing;
    // starting speed between two WayPoints
    private double V_Start;
    // ending speed between two WayPoints
    private double V_End;
    // starting height between two WayPoints
    private double H_Start;
    // ending height between two WayPoints
    private double H_End;
    // starting Azimuth between two WayPoints
    private double Alpha_Start;
    // The optimal time in sec which is necessary to finish the distance between two WayPoints(adjusted by AirplaneCharacteristics)
    private double T_Optimal;
    // The optimal average acceleration which is necessary to finish the distance between two WayPoints(adjusted by AirplaneCharacteristics)
    private double A_Optimal;
    // The optimal average height which is necessary to finish the distance between two WayPoints(adjusted by AirplaneCharacteristics)
    private double H_Optimal;
    // The optimal average angle which is necessary to finish the distance between two WayPoints(adjusted by AirplaneCharacteristics)
    private double Alpha_Optimal;

    public PlaneCalculation() {
        finalPoints = new ArrayList<>();
        geoCalc = new GeodeticCalculator();
        reference = Ellipsoid.WGS84;
        endBearing = new double[1];
    }

    public List<TemporaryPoint> calculateRoute(AirplaneCharacteristics characteristics, List<WayPoint> wayPoints){
        for (WayPoint wayPoint : wayPoints){
            if(characteristics.getMaxSpeed() < wayPoint.getSpeed()) return null;
        }
        List<TemporaryPoint> points = new ArrayList<>();
        finalPoints.clear();
        this.characteristics = characteristics;

        for (int i = 0; i < wayPoints.size() - 1; i++) {
            points.clear();
            // init vars
            init(wayPoints.get(i), wayPoints.get(i + 1));
            // add first WayPoint as TemporaryPoint
            points.add(new TemporaryPoint(Position_Start, V_Start, Alpha_Optimal));

            // determination of TemporaryPoints
            for (int t = 1, size = points.size(); t <= (int)T_Optimal; t++, size++) {
                TemporaryPoint lastPoint = points.get(size - 1);
                TemporaryPoint temporaryPoint = calculateEndingGlobalCoordinates(
                        lastPoint.getGlobalCoordinate(), lastPoint.getCourseInDegrees(), getCurrentDistance(t),
                        getCurrentElevation(t), getCurrentSpeed(t)
                );
                // check the restriction of average change of the course
                if(Math.abs(temporaryPoint.getCourseInDegrees() - lastPoint.getCourseInDegrees()) > characteristics.getMaxDegrees()){
                    t = size = 0;
                    T_Optimal *= 2;
                    setOptimalVars();
                    points.clear();
                    points.add(new TemporaryPoint(Position_Start, V_Start, Alpha_Optimal));
                }else{
                    points.add(temporaryPoint);
                }
            }

            finalPoints.addAll(points);
        }
        return finalPoints;
    }

    // find the coordinates of the next point by given distance, direction angle and the coordinates of the previous point
    private TemporaryPoint calculateEndingGlobalCoordinates(GlobalCoordinates globalCoordinate,
                                                                  double startBearing, double distance,
                                                                  double elevation, double speed){
        GlobalCoordinates coords = geoCalc.calculateEndingGlobalCoordinates(reference, globalCoordinate, startBearing, distance, endBearing);
        return new TemporaryPoint(coords.getLatitude(), coords.getLongitude(), elevation, speed, endBearing[0]);
    }

    // init starting global vars between two WayPoints
    private void init(WayPoint from, WayPoint to){
        V_Start = from.getSpeed();
        V_End = to.getSpeed();
        H_Start = from.getElevation();
        H_End = to.getElevation();

        Position_Start = new GlobalPosition(from.getLatitude(), from.getLongitude(), H_Start);
        GlobalPosition Position_End = new GlobalPosition(to.getLatitude(), to.getLongitude(), H_End);
        GeodeticMeasurement geoMeasurement = geoCalc.calculateGeodeticMeasurement(reference, Position_Start, Position_End);

        Alpha_Start = geoMeasurement.getAzimuth();
        T_Optimal = (2 * geoMeasurement.getPointToPointDistance() ) / ( V_Start + V_End );
        setOptimalVars();
    }

    // determination optimal vars between two WayPoints (adjusted by AirplaneCharacteristics)
    private void setOptimalVars(){
        double V_Delta = V_End - V_Start;
        double H_Delta = H_End - H_Start;

        A_Optimal = V_Delta / T_Optimal;
        H_Optimal = H_Delta / T_Optimal;
        Alpha_Optimal = calculateEndingGlobalCoordinates(
                Position_Start, Alpha_Start, getCurrentDistance(1), getCurrentElevation(1), getCurrentSpeed(1)
        ).getCourseInDegrees();

        if(Math.abs(A_Optimal) > characteristics.getMaxAcceleration()) {
            T_Optimal = Math.abs(V_Delta) / characteristics.getMaxAcceleration();
            A_Optimal = V_Delta / T_Optimal;
            H_Optimal = H_Delta / T_Optimal;
            Alpha_Optimal = calculateEndingGlobalCoordinates(
                    Position_Start, Alpha_Start, getCurrentDistance(1), getCurrentElevation(1), getCurrentSpeed(1)
            ).getCourseInDegrees();
        }

        if(Math.abs(H_Optimal) > characteristics.getMaxElevation()){
            T_Optimal = Math.abs(H_Delta) / characteristics.getMaxElevation();
            A_Optimal = V_Delta / T_Optimal;
            H_Optimal = H_Delta / T_Optimal;
            Alpha_Optimal = calculateEndingGlobalCoordinates(
                    Position_Start, Alpha_Start, getCurrentDistance(1), getCurrentElevation(1), getCurrentSpeed(1)
            ).getCourseInDegrees();
        }
    }

    // methods to get values for each step for TemporaryPoints loop
    private double getCurrentSpeed(int t){
        return V_Start + (t * A_Optimal);
    }

    private double getCurrentElevation(int t){
        return H_Start + (t * H_Optimal);
    }

    private double getCurrentDistance(int t){
        return (getCurrentSpeed(t) + getCurrentSpeed(t-1)) / 2;
    }
}
