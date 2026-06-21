package com.sawari.sawari.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geocode {
    private String place_id;
    private String licence;
    private String osm_type;
    private String osm_id;

    private String[] boundingbox;   // keep as String[]

    private String lat;
    private String lon;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("class")
    private String clazz;   // cannot use 'class'

    private String type;
    private Double importance;
    private String icon;
}
