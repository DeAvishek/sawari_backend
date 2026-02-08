package com.sawari.sawari.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.sawari.sawari.support.EnumValues;
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
    private Integer id;

    @Column(nullable = false,unique = false)
    private String source;

    @Column(nullable = false,unique = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    private EnumValues.TripStatusEnum tripStatus;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private Long distance;

    @Enumerated(EnumType.STRING)
    private EnumValues.PaymentStatusEnum paymentStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private Rider rider;

}
