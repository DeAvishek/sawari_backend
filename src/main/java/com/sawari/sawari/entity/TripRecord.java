package com.sawari.sawari.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
public class TripStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TripId")
    private Integer Id;

    @Column(nullable = false,unique = false)
    private String Source;

    @Column(nullable = false,unique = false)
    private String Destination;

    @Enumerated(EnumType.STRING)
    private EnumValues.TripStatusEnum TripStatus = EnumValues.TripStatusEnum.Requested;

    @Enumerated(EnumType.STRING)
    private EnumValues.PaymentStatusEnum PaymentStatus = EnumValues.PaymentStatusEnum.Pending;

    @CreationTimestamp
    private LocalDateTime CreatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private Rider Rider;

}
