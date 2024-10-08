package com.example.Booking.Services;

import com.example.Booking.BookingRequestListCreator;
import com.example.Booking.DTO.RoomCountDTO;
import com.example.Booking.Entity.Room;
import com.example.Booking.ModelAndSessionNames;
import com.example.Booking.Models.BookingRequest;
import com.example.Booking.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final SessionService sessionService;

    @Autowired
    public RoomService(RoomRepository roomRepository,
                       SessionService sessionService) {
        this.sessionService = sessionService;
        this.roomRepository = roomRepository;
    }

    public void mapToSessionBookingRequestList(Map<String, String> params) {

        List<BookingRequest> bookingRequestList = new BookingRequestListCreator().createByMap(params);
        List<Room> selectedRooms = new ArrayList<>();

        for (int i = 0; i < bookingRequestList.size(); i++)
            selectedRooms.add(new Room());

        sessionService.setAttribute(ModelAndSessionNames.BOOKING_REQUEST_LIST, bookingRequestList);
        sessionService.setAttribute(ModelAndSessionNames.SELECTED_ROOMS, selectedRooms);
    }


    public List<Room> roomsToSelect(Integer selectable_room) {

        List<BookingRequest> bookingRequestList =
                sessionService.getSafeAttributes(ModelAndSessionNames.BOOKING_REQUEST_LIST, BookingRequest.class);

        //Получили параметры брони для дальнейшего запроса в БД
        BookingRequest bookingRequest = bookingRequestList.get(selectable_room - 1);

        //Запрос в БД с этими параметрами брони
        List<Room> findedRooms = roomRepository.findAvailableRooms(
                bookingRequest.getCheckin(),
                bookingRequest.getCheckout(),
                bookingRequest.getAdults() + bookingRequest.getChildren()
        );

        /* ----------   Здесь нужно вычесть выбранные данные... -------- */

        return findedRooms;
    }

    public Room getRoomToSelect(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    public String getTotalRoomsPrice(List<BookingRequest> bookingRequestList,
                                     List<Room> selectedRooms) {
        //СФОРМИРОВАТЬ ПРАЙС
        //Временная колхозная реализация
        /*BookingRequest bookingRequest = bookingRequestList.getFirst();
        Date checkoutDate = bookingRequest.getCheckout();
        Date checkinDate = bookingRequest.getCheckin();
        long diffInMillis = checkoutDate.getTime() - checkinDate.getTime();
        long daysDifferent = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        int totalPrice = 0;
        for (Room room : selectedRooms)
            totalPrice += room.getPrice();
        totalPrice *= (int) daysDifferent;*/

        return "101,572.00";
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

    public List<Integer> getAvailableRoomNumbers(Date checkin,
                                                Date checkout,
                                                int room_id) {
        return roomRepository.getAvailableRoomNumbers(
                checkin,
                checkout,
                room_id
        );
    }
}
