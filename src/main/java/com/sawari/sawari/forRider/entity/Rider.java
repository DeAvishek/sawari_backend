package com.sawari.sawari.forRider.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Rider")
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserId")
    private Integer id;

    @Column(nullable = false,unique = true)
    private String userName;

    @Column(length = 10,unique = true)
    private String phoneNumber;

    private String role;

    private Boolean isVerified;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private List<TripRecord> trips;
}
