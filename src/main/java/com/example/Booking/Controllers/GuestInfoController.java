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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class GuestInfoController {
    @GetMapping("/book/reservation")
    public String createForm(HttpSession httpSession,
                             Model model) {
        List<BookingRequest> bookingRequestList = (List<BookingRequest>) httpSession.getAttribute("bookingRequestList");
        List<Room> selectedRooms = (List<Room>) httpSession.getAttribute("selected_rooms");

        if (bookingRequestList == null || selectedRooms == null)
            return "redirect:/book";

        //СФОРМИРОВАТЬ ПРАЙС
        //Временная колхозная реализация
        BookingRequest bookingRequest = bookingRequestList.getFirst();
        Date checkoutDate = bookingRequest.getCheckout();
        Date checkinDate = bookingRequest.getCheckin();
        long diffInMillis = checkoutDate.getTime() - checkinDate.getTime();
        long daysDifferent = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        int totalPrice = 0;
        for (Room room : selectedRooms)
            totalPrice += room.getPrice();
        totalPrice *= (int) daysDifferent;
        //По-хорошему нужен отдельный класс, который сформирует заявку, часть ее передаст в модель и часть считает с формы самбиченной
        //reservation request service
        //String formattedTotalPrice = String.format("%,.2f", (double) totalPrice / 1000);


        model.addAttribute("selected_rooms", selectedRooms);
        model.addAttribute("total_price", totalPrice);

        return "final-form";
    }

    @PostMapping("/book/reservation")
    @ResponseBody
    public ResponseEntity<String> submitForm() {

        //в классе GuestInfoService сделать запрос на сохранение Transactional в БД с проверкой итоговой на наличие номеров
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Succesful reservation!");
    }
}
