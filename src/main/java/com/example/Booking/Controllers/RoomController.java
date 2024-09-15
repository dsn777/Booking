package com.example.Booking.Controllers;

import com.example.Booking.DTO.RoomCountDTO;
import com.example.Booking.Entity.Room;
import com.example.Booking.Services.RoomService;
import com.example.Booking.Services.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
        roomService.roomsToSelect(
                selectable_room,
                model
        );

        return "rooms";
    }


    @GetMapping("/select_room/{id}")
    public String selectRoom(@PathVariable("id") Room room,
                             HttpSession httpSession) {
        //Room roomToSelect = roomService.getRoomToSelect(id); //достаем из БД номер для выбора
        List<Room> selectedRooms = sessionService.getSafeAttributes("selected_rooms", Room.class); //достаем из сессии и пихаем новый Room
        selectedRooms.set(selectable_room - 1, room);
        sessionService.setAttribute("selected_rooms", selectedRooms);

        return selectable_room != selectedRooms.size() ?
                "redirect:/book/rooms/" + ++selectable_room :
                "redirect:/book/reservation";
    }


    @GetMapping("/room_counts")
    @ResponseBody
    public List<RoomCountDTO> getRoomCounts(@RequestParam Date checkin,
                                            @RequestParam Date checkout,
                                            @RequestParam int guestsnumber) {
        return roomService.getRoomCounts(
                checkin,
                checkout,
                guestsnumber
        );
    }

    @GetMapping("/test")
    @ResponseBody
    public List<String> testMethod(@RequestParam Date checkin,
                                   @RequestParam Date checkout,
                                   @RequestParam Integer room_id) {
        return roomService.getAvailableRoomNumbers(checkin, checkout, room_id);
    }
}

 /*//добавить счетчик текущего номера для выбора
    @GetMapping("/rooms/{id}")
    public String testMethod(@PathVariable Integer id,
                             HttpSession session,
                             Model model) {
        //ДОБАВИТЬ ССЫЛКУ ДЛЯ ВЫБОРА НОМЕРА НА ЕГО ЗАГОЛОВОК


        //Получение следующего номера для брони
        List<BookingRequest> bookingRequestList = (List<BookingRequest>) session.getAttribute("bookingRequestList");
        BookingRequest bookingRequest = bookingRequestList.get(1); //get(i++)?

        //Запрос в БД с этими параметрами брони
        List<Room> findedRooms = roomRepository.findAvailableRooms(
                bookingRequest.getCheckin(),
                bookingRequest.getCheckout(),
                bookingRequest.getAdults() + bookingRequest.getChildren()
        );


        //здесь добавляется номер с id в selected_rooms
        List<Room> selectedRooms = (List<Room>) session.getAttribute("selected_rooms");
        selectedRooms = selectedRooms != null ? selectedRooms : new ArrayList<>() ;
        selectedRooms.add(roomRepository.findById(id).orElse(null));

        //тут вычитаются старые данные count--;
        //findedRooms - selectedRooms!!!!


        //сохранение в сессию и в модель
        session.setAttribute("selected_rooms", selectedRooms);
        model.addAttribute("selected_rooms", selectedRooms);
        model.addAttribute("finded_rooms", findedRooms);

        return (selectedRooms.size() != bookingRequestList.size()) ?
                "rooms" :
                "redirect:/book/create_form/"; //или уже вернуть final_form
    }


    //СФОРМИРОВАТЬ ПРАЙС СУДЯ ПО ДАТАМ И ЦЕНЕ
    @GetMapping("/create_form/{id}")
    public String createForm(HttpSession httpSession,
                             Model model,
                             SessionStatus status,
                             @PathVariable(required = false) Integer id) {
        if (id == null)
            id = 1;

        List<Room> rooms = (List<Room>) model.getAttribute("rooms");
        List<Room> selectedRooms = List.of(rooms.get(id));

        httpSession.setAttribute("selected_rooms", selectedRooms);
        log.info(selectedRooms.toString());
        status.setComplete();

        return "redirect:/book/reservation";
    }*/