package com.jip.booking.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jip
 */
@Data
public class BookingRequest implements Serializable {

    private static final long serialVersionUID = 549753869835937005L;

    private Long carId;

    private String startTime;

    private String endTime;
}
