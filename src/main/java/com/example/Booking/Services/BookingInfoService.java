package com.example.Booking.Services;

import com.example.Booking.Entity.BookingInfoEntity;
import com.example.Booking.Repositories.BookingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookingInfoService {
    private final BookingInfoRepository bookingInfoRepo;
    private final SessionService sessionService;

    @Autowired
    public BookingInfoService(BookingInfoRepository bookingInfoRepo,
                              SessionService sessionService) {
        this.bookingInfoRepo = bookingInfoRepo;
        this.sessionService = sessionService;
    }

    public void save(BookingInfoEntity bookingInfoEntity) {
        bookingInfoRepo.save(bookingInfoEntity);
    }

    public void saveAll(Iterable<BookingInfoEntity> bookingInfoEntities) {
        bookingInfoRepo.saveAll(bookingInfoEntities);
    }
}
