package com.sawari.sawari.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDtoFromUser {
    private String Id;
    private String source;
    private String destination;
    private Double duration;
    private Double distance;
    private String geometry;
}
