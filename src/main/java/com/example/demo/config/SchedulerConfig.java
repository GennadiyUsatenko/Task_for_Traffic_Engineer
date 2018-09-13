package com.example.demo.config;

import com.example.demo.service.PlaneCalculation;
import com.example.demo.model.Airplane;
import com.example.demo.model.TemporaryPoint;
import com.example.demo.repository.AirplaneRepositoryImpl;
import com.example.demo.repository.FlightRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author Gennadiy Usatenko
 * @version 13.09.2018
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {
    private static int numberOfFlights = 1;
    private List<TemporaryPoint> points;
    @Autowired
    private PlaneCalculation planeCalculation;
    @Autowired
    private AirplaneRepositoryImpl airplaneRepository;
    @Autowired
    private FlightRepositoryImpl flightRepository;

    @Scheduled(fixedDelay = 1)
    public void scheduleFixedDelayTask() {
        if( numberOfFlights <= 3){
            if(numberOfFlights > 1)flightRepository.printPatsFlights();
            boolean isTemporaryMode = false;
            Airplane airplane = Airplane.getTestAirplane(numberOfFlights);
            points = planeCalculation.calculateRoute(
                    airplane.getAirplaneCharacteristics(), airplane.getFlight().getWayPoints());

            for(TemporaryPoint point : points){
                airplane.setPosition(point);
                airplane.getFlight().getPassedPoints().add(point);
                airplaneRepository.saveAirplane(airplane, isTemporaryMode);
                isTemporaryMode = true;
            }
            numberOfFlights++;

        }
    }
}
