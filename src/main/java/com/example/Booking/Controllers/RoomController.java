package com.example.Booking.Controllers;

import com.example.Booking.Entity.Room;
import com.example.Booking.Entity.RoomImagePath;
import com.example.Booking.Models.BookingData;
import com.example.Booking.Repositories.ImageRepository;
import com.example.Booking.Repositories.RoomRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/book")
@SessionAttributes("rooms")
public class RoomController {

    private final RoomRepository roomRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository, ImageRepository imageRepository) {
        this.roomRepository = roomRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/rooms")
    public String getRooms(BookingData bookingData,
                           Model model,
                           HttpSession session) {

        log.info("request params : " + bookingData.toString());
        List<Room> rooms = (bookingData.incomplete()) ?
                roomRepository.findAll() :
                roomRepository.findAvailableRooms(
                        bookingData.getCheckin(),
                        bookingData.getCheckout(),
                        bookingData.getGuestsNumber()
                );
        //СФОРМИРОВАТЬ ПРАЙС СУДЯ ПО ДАТАМ И ЦЕНЕ
        session.setAttribute("bookingData", bookingData);
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @GetMapping("/create_form")
    public String createForm(HttpSession httpSession,
                             Model model,
                             SessionStatus status,
                             @PathVariable(name = "room_id", required = false) Integer id) {
        if (id == null)
            id = 1;

        List<Room> rooms = (List<Room>) model.getAttribute("rooms");
        Room sessionRoom = rooms.get(id);
        httpSession.setAttribute("room", sessionRoom);
        log.info(sessionRoom.toString());
        status.setComplete();

        return "redirect:/book/reservation";
    }

    /*@GetMapping("/rest_rooms")
    @ResponseBody
    public List<Room> rooms(@RequestParam(required = false) Integer id) {
        return (id == null) ?
                roomRepository.findAll() :
                List.of(roomRepository.findById(id).orElse(new Room()));
    }

    @GetMapping("/rest_images")
    @ResponseBody
    public List<RoomImagePath> images() {
        return imageRepository.findAll();
    }*/

}
