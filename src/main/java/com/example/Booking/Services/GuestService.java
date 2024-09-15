package com.example.Booking.Services;

import com.example.Booking.Entity.GuestInfoEntity;
import com.example.Booking.Repositories.GuestInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    private final GuestInfoRepository guestInfoRepo;

    @Autowired
    public GuestService(GuestInfoRepository guestInfoRepo) {
        this.guestInfoRepo = guestInfoRepo;
    }

    public GuestInfoEntity getGuest(Long id) {
        return guestInfoRepo.findById(id).orElseThrow();
    }

    public void saveGuest(GuestInfoEntity guestInfoEntity) {
        guestInfoRepo.save(guestInfoEntity);
    }
}
