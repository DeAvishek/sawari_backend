package com.sawari.sawari.common.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SourceDestinationLatAndLong {
    private String sourceLongitude;
    private String sourceLatitude;
    private String destinationLongitude;
    private String destinationLatitude;
}
