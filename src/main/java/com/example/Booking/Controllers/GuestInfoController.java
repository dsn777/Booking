package com.example.Booking.Controllers;

import com.example.Booking.Entity.BookingInfoEntity;
import com.example.Booking.Entity.GuestInfoEntity;
import com.example.Booking.Entity.Room;
import com.example.Booking.ModelAndSessionNames;
import com.example.Booking.Models.BookingRequest;
import com.example.Booking.Models.CardModel;
import com.example.Booking.Models.GuestInfoModel;
import com.example.Booking.Services.BookingInfoService;
import com.example.Booking.Services.GuestService;
import com.example.Booking.Services.RoomService;
import com.example.Booking.Services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//в классе GuestInfoService сделать запрос на сохранение Transactional в БД с проверкой итоговой на наличие номеров
// переименовать контролллер

@Controller
@Slf4j
public class GuestInfoController {

    private final GuestService guestService;
    private final RoomService roomService;
    private final SessionService sessionService;
    private final BookingInfoService bookingInfoService;

    @Autowired
    public GuestInfoController(GuestService guestService,
                               RoomService roomService,
                               SessionService sessionService,
                               BookingInfoService bookingInfoService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.sessionService = sessionService;
        this.bookingInfoService = bookingInfoService;
    }


    @GetMapping("/book/reservation")
    public String createForm(Model model) {

        if (sessionService.isEmpty())
            return "redirect:/book";

        List<BookingRequest> bookingRequestList
                = sessionService.getSafeAttributes(ModelAndSessionNames.BOOKING_REQUEST_LIST, BookingRequest.class);
        List<Room> selectedRooms
                = sessionService.getSafeAttributes(ModelAndSessionNames.SELECTED_ROOMS, Room.class);
        String totalPrice
                = roomService.getTotalRoomsPrice(bookingRequestList, selectedRooms);

        model.addAttribute(ModelAndSessionNames.SELECTED_ROOMS, selectedRooms);
        model.addAttribute(ModelAndSessionNames.TOTAL_PRICE, totalPrice);

        return "final-form";
    }

    @PostMapping("/book/reservation")
    @ResponseBody
    @Transactional
    public synchronized ResponseEntity<?> makeReservation(@ModelAttribute CardModel cardModel,
                                                          @ModelAttribute GuestInfoModel guestInfoModel) {

        List<BookingInfoEntity> totalList = new ArrayList<>();
        GuestInfoEntity thisGuest = guestInfoModel.toEntity();
        guestService.saveGuest(thisGuest);

        List<BookingRequest> bookingRequestList
                = sessionService.getSafeAttributes(ModelAndSessionNames.BOOKING_REQUEST_LIST, BookingRequest.class);
        List<Room> selectedRooms
                = sessionService.getSafeAttributes(ModelAndSessionNames.SELECTED_ROOMS, Room.class);

        BookingRequest first = bookingRequestList.getFirst();
        Date checkin = first.getCheckin();
        Date checkout = first.getCheckout();
        Integer guestsNumber = first.getAdults() + first.getChildren();


        //Для каждого номера формируем заявку
        for (Room room : selectedRooms) {
            //находим первый подходящий номер по категории
            Integer availableRoomNumber = roomService.getAvailableRoomNumbers(
                    checkin,
                    checkout,
                    room.getId()
            ).getFirst();


            //проверка
            if (availableRoomNumber == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Вероятно номер был забронирован, повторите попытку");

            //сохраняем в БД
            BookingInfoEntity bookingInfo = new BookingInfoEntity();
            bookingInfo.setCheck_out(checkout);
            bookingInfo.setCheck_in(checkin);
            bookingInfo.setGuest(thisGuest); // сюда реального гостя из Model
            bookingInfo.setGuests_number(guestsNumber);
            bookingInfo.setRoom(room);
            bookingInfo.setRoom_number(availableRoomNumber);//сюда найденный из БД номер
            bookingInfoService.save(bookingInfo);

            totalList.add(bookingInfo);
        }
        //сделать запрос в БД найти номера
        //если есть, то сформировать заявку-сущность, сохранить ее в БД BookingData
        //сохранить GuestInfo
        //судя по всему вызвать другой сервис и отправить заявку по Email
        //вспомнить данные из сессии и добавить в bookingDataEntity все вместе
        //guestRepo.save(guestInfoModel)
        //bookingDataRepo.save();.....
        sessionService.clear();

        return ResponseEntity.ok(totalList);
    }


    @GetMapping("/get")
    @ResponseBody
    public GuestInfoEntity getGuest(@RequestParam Long id) {
        return guestService.getGuest(id);
    }
}
