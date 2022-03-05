package com.jip.booking.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Jip
 */
@Data
public class Order {
    /**
     * booking order id
     */
    private Long id;

    /**
     * booked car id
     */
    private Long carId;

    /**
     * booking start time
     */
    private LocalDateTime startTime;

    /**
     * booking end time
     */
    private LocalDateTime endTime;

    /**
     * order status
     * 0: canceled
     * 1: booked
     */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Order(Long carId, LocalDateTime startTime, LocalDateTime endTime) {
        this.carId = carId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.status = 0;
    }

    public Order() {
    }

    public enum OrderStatus {
        /**
         * 1: in booking
         * 0: canceled
         */
        IN_BOOKING(1),
        CANCELED(0);

        private final int status;

        OrderStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
        }
    }
}
