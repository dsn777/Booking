package com.example.Booking.RestControllers;

import com.example.Booking.Entity.BookingInfoEntity;
import com.example.Booking.Entity.GuestInfoEntity;
import com.example.Booking.Entity.Room;
import com.example.Booking.Repositories.BookingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/booking_info/")
public class BookingInfoController {
    @Autowired
    private BookingInfoRepository bookingInfoRepo;

    @GetMapping("/get/{id}")
    public BookingInfoEntity getBookingInfo(@PathVariable Long id) {

        BookingInfoEntity entity = bookingInfoRepo.findById(id).orElseThrow();
        return entity;
    }

    @GetMapping("/get/all")
    public Iterable<BookingInfoEntity> getAll() {
        Iterable<BookingInfoEntity> all = bookingInfoRepo.findAll();
        return all;
    }

    @PostMapping("/add")
    public BookingInfoEntity getBookingInfo(@RequestParam Date check_in,
                                            @RequestParam Date check_out,
                                            @RequestParam Integer guests_number,
                                            @RequestParam Room room_id,
                                            @RequestParam GuestInfoEntity guest_id,
                                            @RequestParam Integer room_number) {
        BookingInfoEntity infoEntity = new BookingInfoEntity();
        infoEntity.setCheck_in(check_in);
        infoEntity.setCheck_out(check_out);
        infoEntity.setGuests_number(guests_number);
        infoEntity.setRoom(room_id);
        infoEntity.setGuest(guest_id);
        infoEntity.setRoom_number(room_number);

        bookingInfoRepo.save(infoEntity);

        return infoEntity;
    }

}
