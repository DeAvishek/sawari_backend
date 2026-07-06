package com.sawari.sawari.common.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationWithDriver {
    private Double latitude;
    private Double longitude;
    private String userId;
}
