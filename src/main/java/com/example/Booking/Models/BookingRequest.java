package com.example.Booking.Models;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Date checkin;
    private Date checkout;
    private Integer adults;
    private Integer children;
}
