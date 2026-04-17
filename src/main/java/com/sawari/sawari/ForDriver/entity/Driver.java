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
    private String name;
    @Column(unique = true,length = 10)
    private String phoneNumber;

    @Column(nullable = false,length =6)
    private String Otp;

    private Boolean isVerified;
    private Boolean isOnline;
    private Boolean isOnRide;

    @Column(nullable = false)
    private LocalDateTime otpExpiredAt;
}
