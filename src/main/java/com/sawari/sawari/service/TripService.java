package com.sawari.sawari.service;


import com.sawari.sawari.Repository.RiderRepository;
import com.sawari.sawari.Repository.TripRecordRepository;
import com.sawari.sawari.entity.Rider;
import com.sawari.sawari.entity.TripRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sawari.sawari.support.EnumValues;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TripService {
    @Autowired
    private TripRecordRepository tripRecordRepository;
    @Autowired
    private RiderRepository riderRepository;
    public TripRecord saveTrip(Integer RiderId, TripRecord tripRecord){
        try{
            Rider ExistedRider = riderRepository.findById(RiderId)
                    .orElseThrow(() -> new RuntimeException("Rider not found"));
            tripRecord.setRider(ExistedRider);
            tripRecord.setTripStatus(EnumValues.TripStatusEnum.Requested);
            tripRecord.setPaymentStatus(EnumValues.PaymentStatusEnum.Pending);
            tripRecord.setCreatedAt(LocalDateTime.now());
            TripRecord savedTrip = tripRecordRepository.save(tripRecord);//-->and save trip with respective rider and return the trip record

            ExistedRider.getTrips().add(tripRecord);
            riderRepository.save(ExistedRider); //-->save the trip in rider entity
            return savedTrip;
        }catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
