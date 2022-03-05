package com.jip.booking.service;


import com.jip.booking.entity.Car;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jip
 */
public interface ICarService {

    /**
     * find bookable cars during the given time
     *
     * @param startTime start time
     * @param endTime end time
     * @return list of bookable cars
     */
    List<Car> getBookableCar(LocalDateTime startTime, LocalDateTime endTime);

}
