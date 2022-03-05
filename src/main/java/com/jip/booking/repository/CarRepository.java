package com.jip.booking.repository;

import com.jip.booking.entity.Car;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jip
 */
@Repository
public interface CarRepository {


    Car findById(Long carId);
}
