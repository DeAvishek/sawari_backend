package com.sawari.sawari.pojo;

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
