package com.sawari.sawari.helper;

import org.springframework.stereotype.Component;

@Component
public class StringFormatter {
    public String FormatStringForSrcDestGeocode(Double srcLon, Double srcLat, Double dstLon, Double dstLat){
        String str = String.format(
                "geo:%f,%f;%f,%f",
                srcLon,
                srcLat,
                dstLon,
                dstLat
        );
        return str;
    }
}
