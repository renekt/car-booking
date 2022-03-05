package com.jip.booking.service.impl;

import com.jip.booking.repository.CarRepository;
import com.jip.booking.entity.Car;
import com.jip.booking.repository.OrderRepository;
import com.jip.booking.service.ICarService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jip
 */
@Service
public class CarServiceImpl implements ICarService {
    private final CarRepository carRepository;

    private final OrderRepository orderRepository;

    public CarServiceImpl(CarRepository carRepository, OrderRepository orderRepository) {
        this.carRepository = carRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Car> getBookableCar(LocalDateTime startTime, LocalDateTime endTime) {
        Assert.isTrue(startTime.isBefore(endTime), "Start time must be before end time");
        Assert.isTrue(startTime.isAfter(LocalDateTime.now()), "Start time must be after current time");
        return orderRepository.findBookableCars(startTime, endTime);
    }

}
