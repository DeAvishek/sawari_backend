package com.sawari.sawari.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IsReadyToAcceptRide {
    private Boolean value;
    private String driverId;
}
