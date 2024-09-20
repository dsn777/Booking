package com.example.Booking.Models;

import com.example.Booking.Entity.GuestInfoEntity;
import lombok.Data;

//сюда валидацию
@Data
public class GuestInfoModel {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String country;

    public GuestInfoEntity toEntity() {
        GuestInfoEntity guest = new GuestInfoEntity();
        guest.setFirst_name(firstname);
        guest.setLast_name(lastname);
        guest.setEmail(email);
        guest.setCountry(country);
        guest.setPhone(phone);
        return guest;
    }
}
