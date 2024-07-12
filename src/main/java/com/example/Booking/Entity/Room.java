package com.example.Booking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer guests_number;
    private Integer price;
    private String description;
    private String name;

    @OneToMany(mappedBy = "room_id", cascade = CascadeType.ALL)
    private List<RoomImagePath> image_paths = new ArrayList<>();
}
