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
    private Integer Id;

    @Column(nullable = false,unique = true)
    private String UserName;

    @Column(length = 10 ,unique = true)
    private String PhoneNumber;

    @Column(length = 6)
    private String Otp;


    private Boolean IsVerified;

    @CreationTimestamp
    private LocalDateTime CreatedDate;

    @Column(nullable = false)
    private LocalDateTime OtpExpiredAt;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private List<TripRecord> Trips;
}
