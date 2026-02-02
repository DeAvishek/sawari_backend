package com.sawari.sawari.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
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
    private int Id;

    @Column(nullable = false,unique = true)
    private String UserName;

    @Column(length = 10 ,unique = true)
    private String PhoneNumber;

    @Column(length = 6 ,unique = true)
    private String Otp;

    @Column(columnDefinition = "boolean default false")
    private boolean IsVerified;

    @CreationTimestamp
    private LocalDateTime CreatedDate;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<TripStatus>Trips;
}
