package com.example.Booking.Repositories;


import com.example.Booking.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "SELECT * FROM Room WHERE price = :price", nativeQuery = true)
    List<Room> findByPrice(Integer price);

    @Query(value =
        "SELECT * FROM Room " +
        "WHERE Guests_Number >= :guestsnumber AND Id NOT IN " +
        "(" +
            "SELECT Room_Id FROM RoomAccess " +
            "Where" +
            "(" +
                "Check_in <= :checkin AND Check_out >= :checkout OR " +
                "Check_in <= :checkin AND Check_out >= :checkout OR " +
                "Check_in > :checkin AND Check_in < :checkout OR " +
                "Check_out > :checkin AND Check_out < :checkout" +
            ")" +
        ")",
            nativeQuery = true)
    List<Room> findAvailableRooms(Date checkin, Date checkout, Integer guestsnumber);
}
