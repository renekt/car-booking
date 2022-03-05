package com.jip.booking.repository;

import com.jip.booking.entity.Car;
import com.jip.booking.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jip
 */
@Repository
public interface OrderRepository {


    List<Car> findBookableCars(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    Integer updateOrderStatus(Order order);

    List<Order> findInBookingOrder(@Param("carId") Long carId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    Long save(Order order);

    Order findById(@Param("id") Long id);

    LocalDateTime findFreeStartTime(@Param("carId") Long carId);
}
