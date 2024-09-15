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
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date check_in;
    private Date check_out;
    private String guests_number;

    //mapping
    //@OneToMany
    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonManagedReference
    private Room room;
    //mapping
    @ManyToOne
    @JoinColumn(name = "guest_id")
    @JsonManagedReference
    private GuestInfoEntity guest;
    private String room_number;
}
