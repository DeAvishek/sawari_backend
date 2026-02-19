package com.sawari.sawari.pojo;
import lombok.*;
@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class RedisTripSession {
    private Integer riderId;
    private Integer driverId;
}
