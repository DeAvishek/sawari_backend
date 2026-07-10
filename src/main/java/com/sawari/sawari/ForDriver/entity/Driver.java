package com.sawari.sawari.ForDriver.entity;

import com.sawari.sawari.forRider.entity.TripRecord;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DriverId")
    private Integer id;
    @Column(nullable = false)
    private String userName;
    @Column(unique = true,length = 10)
    private String phoneNumber;

    private Boolean isVerified;
    private Boolean isOnline;
    private Boolean isOnRide; //this will updated when user driver accepts a ride
    @Column(nullable = false)
    private String vehicleType;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private List<TripRecord>trips;
}
