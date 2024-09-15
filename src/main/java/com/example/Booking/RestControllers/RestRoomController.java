package com.example.Booking.RestControllers;

import com.example.Booking.Entity.Room;
import com.example.Booking.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RestRoomController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/get/{id}")
    public Room getRoom(@PathVariable Integer id) {
        Room room = roomRepository.findById(id).orElseThrow();;
        return room;
    }
}
