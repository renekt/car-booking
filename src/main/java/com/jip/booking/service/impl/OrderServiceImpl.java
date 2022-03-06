package com.jip.booking.service.impl;

import com.jip.booking.entity.Car;
import com.jip.booking.entity.Order;
import com.jip.booking.repository.CarRepository;
import com.jip.booking.repository.OrderRepository;
import com.jip.booking.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jip
 */
@Service
public class OrderServiceImpl implements IOrderService {
    private static final Set<Long> LOCKED_KEYS = new HashSet<>();

    private final OrderRepository orderRepository;

    private final CarRepository carRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CarRepository carRepository) {
        this.orderRepository = orderRepository;
        this.carRepository = carRepository;
    }


    @Override
    public String cancelBookedOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        Assert.isTrue(order != null, "Order not found");
        Assert.isTrue(order.getStatus() == Order.OrderStatus.IN_BOOKING.getStatus(), "Order is not in booking status");
        order.setStatus(Order.OrderStatus.CANCELED.getStatus());
        orderRepository.updateOrderStatus(order);
        return "success";
    }


    @Override
    public LocalDateTime getFreeStartTime(Long carId) {
        return orderRepository.findFreeStartTime(carId);
    }

    @Override
    public List<Order> findInBookingOrders(Long carId, LocalDateTime startTime, LocalDateTime endTime) {
        return orderRepository.findInBookingOrder(carId, startTime, endTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createBookingOrder(Long carId, LocalDateTime startTime, LocalDateTime endTime)  {
        Assert.isTrue(startTime.isAfter(LocalDateTime.now()), "Start time must be after current time");
        Assert.isTrue(startTime.isBefore(endTime), "Start time must be before end time");
        Assert.notNull(carId, "carId must not be null");

        try {
            // acquire lock
            lock(carId);
            Car car = carRepository.findById(carId);
            Assert.isTrue(car != null, "Car not found");

            List<Order> inBookingOrder = orderRepository.findInBookingOrder(carId, startTime, endTime);
            Assert.isTrue(inBookingOrder.isEmpty(), "Car is already booked");

            Order order = new Order(car.getId(), startTime, endTime);
            order.setStatus(Order.OrderStatus.IN_BOOKING.getStatus());
            orderRepository.save(order);
        } catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally{
            unlock(carId);
        }

        return "success";
    }

    private void lock(Long key) throws InterruptedException {
        synchronized (LOCKED_KEYS) {
            while (!LOCKED_KEYS.add(key)) {
                LOCKED_KEYS.wait();
            }
        }
    }

    private void unlock(Long key) {
        synchronized (LOCKED_KEYS) {
            LOCKED_KEYS.remove(key);
            LOCKED_KEYS.notifyAll();
        }
    }
}
