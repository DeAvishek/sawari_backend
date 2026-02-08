package com.sawari.sawari.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "User")
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

    @Column(length = 6)
    private String otp;


    private Boolean isVerified;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime otpExpiredAt;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private List<TripRecord> trips;
}
