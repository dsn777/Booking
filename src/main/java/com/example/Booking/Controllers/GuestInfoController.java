package com.example.Booking.Controllers;

import com.example.Booking.Entity.Room;
import com.example.Booking.Models.BookingData;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class GuestInfoController {
    @GetMapping("/book/reservation")
    public String test(HttpSession httpSession,
                       Model model) {
        BookingData data = (BookingData) httpSession.getAttribute("bookingData");
        Room sessionRoom = (Room) httpSession.getAttribute("room");

        if (sessionRoom == null || data == null)
            return "redirect:/book";

        log.info(data.toString());
        log.info(sessionRoom.toString());
        return "final-form";
    }

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> test() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("custom_header", "header was added")
                .body("All good man");

    }
    /*@PostMapping
    public String acceptReservation() {

        return "good baby)";
    }*/
}
