//package com.sawari.sawari.ForDriver.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "DriverLocation")
//public class DriverLocation {
//    @Column(nullable = false)
//    private Double Latitude;
//    @Column(nullable = false)
//    private Double Longitude;
//    @Column(nullable = false)
//    private LocalDateTime updatedAt;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "DriverId")
//    private Driver driver;
//}
