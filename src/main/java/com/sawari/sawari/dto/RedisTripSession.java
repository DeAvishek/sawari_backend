package com.sawari.sawari.dto;
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
    private Double distance;
    private Double duration;
    private Integer riderId;
}
