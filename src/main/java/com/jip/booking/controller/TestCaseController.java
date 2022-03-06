package com.jip.booking.controller;

import com.jip.booking.entity.Car;
import com.jip.booking.service.ICarService;
import com.jip.booking.service.IOrderService;
import com.jip.booking.service.dto.BookingRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Jip
 */
@RestController
@RequestMapping("/api/booking/")
@Api(tags = "Booking API")
public class TestCaseController {
    private static final String DATETIME_STRING_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final IOrderService orderService;

    private final ICarService carService;


    public TestCaseController(IOrderService orderService, ICarService carService) {
        this.orderService = orderService;
        this.carService = carService;
    }

    @GetMapping("/bookableCars")
    @ApiOperation(value = "Get all bookable cars")
    public List<Car> bookableCars(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern(DATETIME_STRING_PATTERN));
        LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(DATETIME_STRING_PATTERN));
        return carService.getBookableCar(start, end);
    }

    @PostMapping("/new")
    @ApiOperation(value = "Create new booking")
    public String newBooking(@RequestBody BookingRequest request) {
        LocalDateTime startTime = LocalDateTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern(DATETIME_STRING_PATTERN));
        LocalDateTime endTime = LocalDateTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern(DATETIME_STRING_PATTERN));
        return orderService.createBookingOrder(request.getCarId(), startTime, endTime);

    }

    @GetMapping("/cancel/{orderId}")
    @ApiOperation(value = "Cancel booking")
    public String cancelBooking(@PathVariable("orderId") Long orderId) {
        return orderService.cancelBookedOrder(orderId);
    }

}
