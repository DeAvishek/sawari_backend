package com.sawari.sawari.forRider.service.genral;


import com.sawari.sawari.common.dto.RedisTripSession;
import com.sawari.sawari.forRider.Repository.RiderRepository;
import com.sawari.sawari.forRider.Repository.TripRecordRepository;
import com.sawari.sawari.forRider.entity.Rider;
import com.sawari.sawari.forRider.entity.TripRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sawari.sawari.common.support.EnumValues;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TripService {
    @Autowired
    private TripRecordRepository tripRecordRepository;
    @Autowired
    private RiderRepository riderRepository;
    @Transactional
    public TripRecord saveTrip(RedisTripSession redisTripSession){
        try{
            Rider ExistedRider = riderRepository.findById(redisTripSession.getRiderId())
                    .orElseThrow(() -> new RuntimeException("Rider not found"));
              TripRecord tripRecord = new TripRecord();
              tripRecord.setSource(redisTripSession.getSource());
              tripRecord.setDestination(redisTripSession.getDestination());
              tripRecord.setTripStatus(EnumValues.TripStatusEnum.Requested);
              tripRecord.setPaymentStatus(EnumValues.PaymentStatusEnum.Pending);
              tripRecord.setFare(1000);
              tripRecord.setDistance((long)Math.ceil(redisTripSession.getDistance()));
              tripRecord.setDuration((long)Math.ceil(redisTripSession.getDuration()));
              tripRecord.setCreatedAt(LocalDateTime.now());
              tripRecord.setRider(ExistedRider);
              tripRecord.setDriver(null);
              ExistedRider.getTrips().add(tripRecord);
              riderRepository.save(ExistedRider);
              return tripRecordRepository.save(tripRecord);
        }catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
