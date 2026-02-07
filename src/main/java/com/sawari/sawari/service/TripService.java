package com.sawari.sawari.service;


import com.sawari.sawari.Repository.RiderRepository;
import com.sawari.sawari.Repository.TripRecordRepository;
import com.sawari.sawari.entity.Rider;
import com.sawari.sawari.entity.TripRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sawari.sawari.support.EnumValues;

import java.util.ArrayList;

@Service
@Slf4j
public class TripService {
    @Autowired
    private TripRecordRepository tripRecordRepository;
    @Autowired
    private RiderRepository riderRepository;
    public void SaveTrip(Integer RiderId, TripRecord tripRecord){
        try{
            Rider ExistedRider = riderRepository.findById(RiderId)
                    .orElseThrow(() -> new Exception("Rider not found"));
            tripRecord.setRider(ExistedRider);
            tripRecord.setTripStatus(EnumValues.TripStatusEnum.Requested);
            tripRecord.setPaymentStatus(EnumValues.PaymentStatusEnum.Pending);
            tripRecordRepository.save(tripRecord);//-->and save trip with respective rider

            ExistedRider.getTrips().add(tripRecord);
            riderRepository.save(ExistedRider); //-->save the trip in rider entity
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
