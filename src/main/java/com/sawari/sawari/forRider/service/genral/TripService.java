package com.sawari.sawari.forRider.service.genral;


import com.sawari.sawari.forRider.Repository.RiderRepository;
import com.sawari.sawari.forRider.Repository.TripRecordRepository;
import com.sawari.sawari.forRider.entity.Rider;
import com.sawari.sawari.forRider.entity.TripRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sawari.sawari.common.support.EnumValues;

import java.time.LocalDateTime;

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
