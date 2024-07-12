package com.example.Booking.Repositories;

import com.example.Booking.Entity.RoomImagePath;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<RoomImagePath, Integer> {
}
// <img src="/room_images/room_1.1_1jpg"/>