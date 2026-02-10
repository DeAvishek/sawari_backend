package com.sawari.sawari.service;

import com.sawari.sawari.Repository.RiderRepository;
import com.sawari.sawari.Repository.TripRecordRepository;
import com.sawari.sawari.entity.Rider;
import com.sawari.sawari.entity.TripRecord;
import com.sawari.sawari.support.EnumValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    RiderRepository riderRepository;

    @Mock
    TripRecordRepository tripRecordRepository;

    @InjectMocks
    TripService tripService;

    @Test
    void SaveTripShouldSaveTripSuccessfully(){
        Rider rider = new Rider();
        rider.setId(2);
        rider.setUserName("user");
        rider.setIsVerified(true);
        rider.setPhoneNumber("123456");

        Mockito.when(riderRepository.findById(rider.getId())).thenReturn(Optional.of(rider));
        TripRecord tripRecord = new TripRecord();
        tripRecord.setRider(rider);
        tripRecord.setId(38);
        tripRecord.setTripStatus(EnumValues.TripStatusEnum.Accepted);
        tripRecord.setPaymentStatus(EnumValues.PaymentStatusEnum.Pending);
        Mockito.when(tripRecordRepository.save(tripRecord)).thenReturn(tripRecord);
        rider.setTrips(new ArrayList<>());
        rider.getTrips().add(tripRecord);
        Mockito.when(riderRepository.save(rider)).thenReturn(rider);
        TripRecord result = tripService.SaveTrip(rider.getId(),tripRecord);

        Assertions.assertEquals(result.getId(),tripRecord.getId());
    }

    @Test
    void SaveTripWhenRiderNotFound(){
        Integer riderId = 2;
        Mockito.when(riderRepository.findById(riderId)).thenReturn(Optional.empty());
        TripRecord result = tripService.SaveTrip(riderId,new TripRecord());

        //test
        Assertions.assertNull(result);
    }
}