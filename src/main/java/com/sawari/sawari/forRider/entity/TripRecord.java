package com.sawari.sawari.forRider.entity;
import com.sawari.sawari.ForDriver.entity.Driver;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.sawari.sawari.common.support.EnumValues;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Trip")
public class TripRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TripId")
    private Long id;

    @Column(nullable = false,unique = false)
    private String source;

    @Column(nullable = false,unique = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    private EnumValues.TripStatusEnum tripStatus;

    @Enumerated(EnumType.STRING)
    private EnumValues.PaymentStatusEnum paymentStatus;

    @Column(nullable = false)
    private Integer fare;

    @Column(nullable = false)
    private Long distance;

    @Column(nullable = false)
    private Long duration;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DriverId")
    private Driver driver;

}
//this is single source of truth and get touched any tip status is being changed or payment status is being changed