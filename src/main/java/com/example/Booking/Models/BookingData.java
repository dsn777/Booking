package com.example.Booking.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class BookingData {
    private Date checkin;
    private Date checkout;
    private Integer guestsNumber;

    public boolean incomplete() {
        return checkin == null || !(checkout != null & guestsNumber != null);
    }
}
