package com.jip.booking.service;


import com.jip.booking.entity.Order;

import javax.sql.rowset.CachedRowSet;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jip
 */
public interface IOrderService {
    /**
     * cancel booking,update car status and order status
     *
     * @param orderId order id
     * @return success
     */
    String cancelBookedOrder(Long orderId);

    /**
     * rent car,create a new order and update order status
     *
     * @param carId car id
     * @param startTime start date
     * @param endTime end date
     * @return success
     */
    String createBookingOrder(Long carId, LocalDateTime startTime, LocalDateTime endTime);


    /**
     * get free time for car
     *
     * @param carId
     * @return
     */
    LocalDateTime getFreeStartTime(Long carId);

    /**
     * find in booking status order by car id
     * @param carId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Order> findInBookingOrders(Long carId, LocalDateTime startTime, LocalDateTime endTime);
}
