package com.sawari.sawari.controller;
import com.sawari.sawari.dto.AutocompleteLocation;
import com.sawari.sawari.dto.DirectionResponse;
import com.sawari.sawari.dto.SourceDestinationLatAndLong;
import com.sawari.sawari.service.GetDirectionsLIq;
import com.sawari.sawari.service.GetLatLong;
import com.sawari.sawari.service.GetSuggestionServiceForLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/get")
public class GetGeoDetailsOfLocationAndDirectionController {
    @Autowired
    private GetLatLong getLatLong;

    @Autowired
    private GetSuggestionServiceForLocation getSuggestionServiceForLocation;

    @Autowired
    private GetDirectionsLIq getDirectionsLIq;
    @GetMapping("/location/{location}")
    public ResponseEntity<String> GetDetailsFromLocationIq(@PathVariable String location){
        return getLatLong.GetLatAndLongForLocation(location);
    }
    //it will return list of suggestion for a particular instance of location the suggestions leads to auto complete
    @GetMapping("/autocomplete/{location}")
    public ResponseEntity<List<AutocompleteLocation>>getDetailsFromLocationIq(@PathVariable String location){
        return getSuggestionServiceForLocation.getSuggestion(location);
    }

    @PostMapping("/src_dest/direction")
    public ResponseEntity<?>getDirectionWithGeometry(@RequestBody SourceDestinationLatAndLong body){
        try {
            DirectionResponse response = getDirectionsLIq.getDirections(body);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
