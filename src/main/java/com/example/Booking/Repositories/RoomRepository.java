package com.example.Booking.Repositories;


import com.example.Booking.DTO.RoomCountDTO;
import com.example.Booking.Entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;


public interface RoomRepository extends CrudRepository<Room, Integer> {

    @Query(value =
                    "SELECT * FROM room " +
                    "WHERE " +
                    "guests_number >= :guestsnumber " +
                    "AND " +
                    "id NOT IN " +
                    "( " +
                        "SELECT DISTINCT room_id FROM bookinginfo " +
                        "WHERE " +
                        "(" +
                            "check_in >= :checkin AND check_in < :checkout OR " +
                            "check_out > :checkin AND check_out <= :checkout OR " +
                            "check_in <= :checkin AND check_out >= :checkout " +
                        ") " +
                    ") ",
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

    @Query(
            value = "SELECT DISTINCT room_number " +
                    "FROM BookingInfo " +
                    "WHERE room_id = :room_id " +
                    "AND room_number NOT IN " +
                    "( " +
                        "SELECT room_number FROM bookinginfo " +
                        "WHERE " +
                        "(" +
                            "check_in >= :checkin AND check_in < :checkout OR " +
                            "check_out > :checkin AND check_out <= :checkout OR " +
                            "check_in <= :checkin AND check_out >= :checkout " +
                        ")" +
                    ")",
            nativeQuery = true
    )
    List<Integer> getAvailableRoomNumbers(Date checkin, Date checkout, Integer room_id);
}


/*
    будто можно упростить
    ЗАПРОС НЕКОРРЕКТЕН!!!!!!!!!!!!!!!!!
    @Query(value =
            "SELECT distinct Id, Guests_Number, Price, Name, description FROM Room " +
                    "INNER JOIN " +
                    "(" +
                        "SELECT Room_id " +
                        "FROM BookingInfo " +
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
    List<Room> findAvailableRooms(Date checkin, Date checkout, Integer guestsnumber);*/