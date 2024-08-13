package com.example.Booking.Services;

import com.example.Booking.BookingDataCreator;
import com.example.Booking.Entity.Room;
import com.example.Booking.Models.BookingRequest;
import com.example.Booking.Repositories.RoomRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void formBookingRequestList(
            Map<String, String> params,
            HttpSession httpSession) {

        List<BookingRequest> bookingRequestList = new BookingDataCreator().createByMap(params);
        List<Room> selectedRooms = new ArrayList<>();

        for (int i = 0; i < bookingRequestList.size(); i++)
            selectedRooms.add(new Room());

        httpSession.setAttribute("bookingRequestList", bookingRequestList);
        httpSession.setAttribute("selected_rooms", selectedRooms);
    }
}
