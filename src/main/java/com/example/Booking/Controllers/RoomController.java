package com.example.Booking.Controllers;

import com.example.Booking.Entity.Room;
import com.example.Booking.ModelAndSessionNames;
import com.example.Booking.Services.RoomService;
import com.example.Booking.Services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/book")
public class RoomController {

    private final SessionService sessionService;
    private final RoomService roomService;
    private Integer selectable_room = 1;


    @Autowired
    public RoomController(SessionService sessionService,
                          RoomService roomService) {
        this.sessionService = sessionService;
        this.roomService = roomService;
    }

    
    @GetMapping("/rooms")
    public String mapToSessionBookingRequestList(@RequestParam Map<String, String> params) {
        this.selectable_room = 1;
        roomService.mapToSessionBookingRequestList(params);
        return "redirect:/book/rooms/" + selectable_room;
    }


    @GetMapping("/rooms/{selectable_room}")
    public String roomsToSelect(@PathVariable int selectable_room,
                                Model model) {
        this.selectable_room = selectable_room;

        List<Room> findedRooms =
                roomService.roomsToSelect(selectable_room);
        List<Room> selectedRooms =
                sessionService.getSafeAttributes(ModelAndSessionNames.SELECTED_ROOMS, Room.class);

        model.addAttribute(ModelAndSessionNames.SELECTED_ROOMS, selectedRooms);
        model.addAttribute(ModelAndSessionNames.FINDED_ROOMS, findedRooms);

        return "rooms";
    }


    @GetMapping("/select_room/{id}")
    public String selectRoom(@PathVariable("id") Room room) {
        List<Room> selectedRooms
                = sessionService.getSafeAttributes(ModelAndSessionNames.SELECTED_ROOMS, Room.class); //достаем из сессии и пихаем новый Room
        selectedRooms.set(selectable_room - 1, room);
        sessionService.setAttribute(ModelAndSessionNames.SELECTED_ROOMS, selectedRooms);

        return selectable_room != selectedRooms.size() ?
                "redirect:/book/rooms/" + ++selectable_room :
                "redirect:/book/reservation";
    }
}


