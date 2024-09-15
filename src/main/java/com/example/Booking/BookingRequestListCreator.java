package com.example.Booking;

import com.example.Booking.Models.BookingRequest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingRequestListCreator {
    public List<BookingRequest> createByMap(Map<String, String> params) {
        List<BookingRequest> dataList = new ArrayList<>();
        Date checkin = Date.valueOf(params.get("checkin"));
        Date checkout = Date.valueOf(params.get("checkout"));

        for (int i = 0; i < (params.size() - 2) / 2; i++)
            dataList.add(new BookingRequest(
                    checkin,
                    checkout,
                    Integer.valueOf(params.get("adults" + i)),
                    Integer.valueOf(params.get("children" + i))
            ));

        return dataList;
    }
}
