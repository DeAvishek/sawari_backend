package com.sawari.sawari.service;
import com.sawari.sawari.pojo.Geocode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GetLatLong {
    private static String LocationIQApiKey = "pk.7d3dc7f86db9c8b5c9adbe39d87df682";  //need to set the Api key in env
    private static final String Url = "https://us1.locationiq.com/v1/";
    public ResponseEntity<String> GetLatAndLongForLocation(String Location){
        try{
            String uri = String.format(
                    "%ssearch?key=%s&q=%s&format=json&",
                    Url,
                    LocationIQApiKey,
                    Location
            );
            RestTemplate restTemplate = new RestTemplate();
            Geocode[] response = restTemplate.getForObject(uri, Geocode[].class);
//            todo remove just for checking
            System.out.println(response[0]);
            return new ResponseEntity<>(response[0].getLat()+response[0].getLon(), HttpStatus.OK);
        }catch (Exception e){
            log.error("Failed to get Geocode",e.getMessage());
            return new ResponseEntity<>("Failed to get Geocode",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
