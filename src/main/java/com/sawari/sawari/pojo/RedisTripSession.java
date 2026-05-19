package com.sawari.sawari.pojo;
import lombok.*;
@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class RedisTripSession {
    private String source;
    private String destination;
    private String geometry;
    private Double duration;
    private Double distance;
    private Integer riderId;
}
