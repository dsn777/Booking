package com.example.Booking.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping("/book")
    public String book() {
        return "home";
    }
}
