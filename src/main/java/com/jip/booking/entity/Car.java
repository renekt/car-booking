package com.jip.booking.entity;


import lombok.Data;

/**
 * @author Jip
 */
@Data
public class Car {
    /**
     * carId
     */
    private Long id;

    /**
     * carModel
     */
    private String model;

    public Car(String model) {
        this.model = model;
    }

    public Car() {
    }


}
