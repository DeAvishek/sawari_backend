package com.sawari.sawari.service;

import com.sawari.sawari.pojo.AutocompleteLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class GetSuggestionServiceForLocation {
    @Value("${LocationIq.account.api}")
    private String LocationIQApiKey;
    private static final String Url = "https://api.locationiq.com/v1/";
    public ResponseEntity<List<AutocompleteLocation>> getSuggestion(String location){
        try{
            String url = String.format(
                    "%sautocomplete?key=%s&q=%s&limit=5&dedupe=1",
                    Url,
                    LocationIQApiKey,
                    location
                    );
            RestTemplate restTemplate = new RestTemplate();
            AutocompleteLocation [] response = restTemplate.getForObject(url, AutocompleteLocation[].class);
            List<AutocompleteLocation> suggetions = response!=null? Arrays.asList(response) :new ArrayList<>();
            return new ResponseEntity<>(suggetions, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
