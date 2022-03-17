package com.jip.booking.service;

import com.jip.booking.entity.Car;
import com.jip.booking.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * @author Jip
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class CarBookingTest {
    /**
     * car is free from this time
     * means the car is bookable after this time ,and not bookable before this time
     * initialized before each test case
     */
    LocalDateTime freeStartTime;

    /**
     * tmp carId
     * initialized before each test case
     */
    Long carId;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICarService carService;

    @BeforeEach
    void setUp() {
        carId = 1L;
        LocalDateTime maxEndTime = orderService.getFreeStartTime(carId);
        freeStartTime = maxEndTime.plusDays(1);
    }

    @Test
    void getBookableCar() {
        List<Car> bookableCar = carService.getBookableCar(freeStartTime, freeStartTime.plusDays(1));
        assertThat(bookableCar).isNotEmpty();
        List<Long> bookableCarIds = bookableCar.stream().map(Car::getId).collect(Collectors.toList());
        assertThat(bookableCarIds).contains(carId);
    }

    @Test
    void bookingCar() {
        LocalDateTime endTime = freeStartTime.plusDays(1);
        assertThat(orderService.createBookingOrder(carId, freeStartTime, endTime)).isEqualTo("success");
        List<Order> afterInBookingOrders = orderService.findInBookingOrders(carId, freeStartTime, endTime);
        assertThat(afterInBookingOrders).isNotEmpty().size().isEqualTo(1);
        // duplicate booking, throw exception
        assertThrows(IllegalArgumentException.class, () -> orderService.createBookingOrder(carId, freeStartTime, endTime));
    }

    @Test
    void cancelBooking() {
        LocalDateTime endTime = freeStartTime.plusDays(1);
        orderService.createBookingOrder(carId, freeStartTime, endTime);
        List<Order> inBookingOrders = orderService.findInBookingOrders(carId, freeStartTime, endTime);
        Long orderId = inBookingOrders.get(0).getId();
        assertThat(orderService.cancelBookedOrder(orderId)).isEqualTo("success");

        // duplicate cancel, throw exception
        assertThrows(IllegalArgumentException.class, () -> orderService.cancelBookedOrder(orderId));

    }

    @Test
    void bookingCarBeforeCurrentTime() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = freeStartTime.plusDays(1);
        assertThrows(IllegalArgumentException.class, () -> orderService.createBookingOrder(carId, startTime, endTime));
    }

    @Test
    void endTimeBeforeStartTime() {
        LocalDateTime startTime = freeStartTime.plusDays(2);
        LocalDateTime endTime = freeStartTime.plusDays(1);
        assertThrows(IllegalArgumentException.class, () -> orderService.createBookingOrder(carId, startTime, endTime));
    }


}