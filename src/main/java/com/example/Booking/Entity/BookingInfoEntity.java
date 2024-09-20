package com.example.Booking.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name="BookingInfo")
@Getter
@Setter
public class BookingInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date check_in;
    private Date check_out;
    private Integer guests_number;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonManagedReference
    private Room room;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    @JsonManagedReference
    private GuestInfoEntity guest;
    private Integer room_number;
}
