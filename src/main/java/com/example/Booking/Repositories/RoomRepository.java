package com.example.Booking.Repositories;


import com.example.Booking.DTO.RoomCountDTO;
import com.example.Booking.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value =
            "SELECT distinct Id, Guests_Number, Price, Name, description, Count FROM Room " +
                    "INNER JOIN " +
                    "(" +
                        "SELECT Room_id, COUNT(room_id) as Count " +
                        "FROM RoomAccess " +
                        "Group by Room_id, Check_in, Check_out " +
                        "having not " +
                        "(" +
                            "Check_in <= :checkin AND Check_out >= :checkout OR " +
                            "Check_in > :checkin AND Check_in < :checkout OR " +
                            "Check_out > :checkin AND Check_out < :checkout " +
                        ")" +
                    ") as Table_Temp ON Room.Id = Table_Temp.Room_Id " +
                    "Where guests_number >= :guestsnumber",
            nativeQuery = true)
    List<Room> findAvailableRooms(Date checkin, Date checkout, Integer guestsnumber);

    @Query(value =
            "SELECT distinct Room_id, Count FROM Room " +
                    "INNER JOIN " +
                    "(" +
                    "SELECT Room_id, COUNT(room_id) as Count " +
                    "FROM RoomAccess " +
                    "Group by Room_id, Check_in, Check_out " +
                    "having not " +
                    "(" +
                    "Check_in <= :checkin AND Check_out >= :checkout OR " +
                    "Check_in > :checkin AND Check_in < :checkout OR " +
                    "Check_out > :checkin AND Check_out < :checkout " +
                    ")" +
                    ") as Table_Temp ON Room.Id = Table_Temp.Room_Id " +
                    "Where guests_number >= :guestsnumber",
            nativeQuery = true
    )
    List<RoomCountDTO> getRoomCounts(Date checkin, Date checkout, Integer guestsnumber);


    //List<Room> availableRooms(Date checkin, Date checkout);
    //List<Room> findRoomsWithParams(Date checkin, Date checkout, Integer guestsnumber)
}
