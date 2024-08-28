package com.example.Booking.Services;

import com.example.Booking.BookingDataCreator;
import com.example.Booking.DTO.RoomCountDTO;
import com.example.Booking.Entity.Room;
import com.example.Booking.Models.BookingRequest;
import com.example.Booking.Repositories.RoomRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
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


    public void formBookingRequestList(Map<String, String> params,
                                       HttpSession httpSession) {

        List<BookingRequest> bookingRequestList = new BookingDataCreator().createByMap(params);
        List<Room> selectedRooms = new ArrayList<>();

        for (int i = 0; i < bookingRequestList.size(); i++)
            selectedRooms.add(new Room());

        httpSession.setAttribute("bookingRequestList", bookingRequestList);
        httpSession.setAttribute("selected_rooms", selectedRooms);
    }


    public void roomsToSelect(Integer selectable_room,
                              Model model,
                              HttpSession httpSession) {
        List<Room> selectedRooms = (List<Room>) httpSession.getAttribute("selected_rooms");
        List<BookingRequest> bookingRequestList = (List<BookingRequest>) httpSession.getAttribute("bookingRequestList");
        BookingRequest bookingRequest = bookingRequestList.get(selectable_room - 1); //get(i++)?

        //Запрос в БД с этими параметрами брони
        List<Room> findedRooms = roomRepository.findAvailableRooms(
                bookingRequest.getCheckin(),
                bookingRequest.getCheckout(),
                bookingRequest.getAdults() + bookingRequest.getChildren()
        );

        /* ----------   Здесь нужно вычесть выбранные данные... -------- */

        //передача данных с учетом вычтенных
        model.addAttribute("selected_rooms", selectedRooms);
        model.addAttribute("finded_rooms", findedRooms);
    }


    public List<Room> selectRoom(Integer id,
                                 HttpSession httpSession,
                                 Integer selectable_room) {

        //получить из сессии уже выбранные номера
        List<Room> selectedRooms = (List<Room>) httpSession.getAttribute("selected_rooms");

        //найти в базе данных новый выбранный номер
        Room roomToSelect = roomRepository.findById(id).orElse(null);

        //добавить в сессию еще один выбранный номер
        selectedRooms.set(selectable_room - 1, roomToSelect);
        return selectedRooms;
    }


    public List<RoomCountDTO> getRoomCounts(Date checkin,
                                            Date checkout,
                                            int guestsnumber) {
        return roomRepository.getRoomCounts(
                checkin,
                checkout,
                guestsnumber
        );
    }
}
