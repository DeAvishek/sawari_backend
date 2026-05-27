package com.sawari.sawari.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutocompleteLocation {
    private String place_id;
    private String lat;
    private String lon;
    private String display_name;
}
