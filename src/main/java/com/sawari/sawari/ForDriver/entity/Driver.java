package com.sawari.sawari.ForDriver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private Boolean isOnRide;
    @Column(nullable = false)
    private String vehicleType;
}
