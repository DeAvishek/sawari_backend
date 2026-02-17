package com.sawari.sawari.service;

import com.sawari.sawari.pojo.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GetDirectionsLIq {
    private static String LocationIQApiKey = "pk.7d3dc7f86db9c8b5c9adbe39d87df682"; //todo
    private static final String Url = "https://us1.locationiq.com/v1/directions/";
    //hardcoded need to update
    private Double srcLat = 51.514156;
    private Double srcLon = -0.12070277;
    private Double dstLat = 51.507996;
    private Double dstLon = -0.12360937;
    public ResponseEntity<DirectionResponse> getDirections() {
        try{
            String uri = String.format(
                    "%sdriving/%f,%f;%f,%f?key=%s&steps=true&alternatives=true&geometries=polyline&overview=full",
                    Url,
                    srcLon,
                    srcLat,
                    dstLon,
                    dstLat,
                    LocationIQApiKey
            );
            System.out.println(uri); //todo to remove
            RestTemplate restTemplate = new RestTemplate();
            DirectionResponse response =  restTemplate.getForObject(uri, DirectionResponse.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error while fetching directions", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
