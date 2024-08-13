package com.example.Booking.Controllers;

import com.example.Booking.Entity.Room;
import com.example.Booking.Models.BookingRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class GuestInfoController {
    @GetMapping("/book/reservation")
    public String test(HttpSession httpSession,
                       Model model) {
        List<BookingRequest> bookingRequestList = (List<BookingRequest>) httpSession.getAttribute("bookingRequestList");
        List<Room> selectedRooms = (List<Room>) httpSession.getAttribute("selected_rooms");

        if (selectedRooms == null || bookingRequestList == null)
            return "redirect:/book";

        model.addAttribute("selected_rooms", selectedRooms);
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
}
