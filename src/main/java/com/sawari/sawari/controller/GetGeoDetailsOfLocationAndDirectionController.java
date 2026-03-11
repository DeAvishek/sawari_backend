package com.sawari.sawari.controller;
import com.sawari.sawari.pojo.AutocompleteLocation;
import com.sawari.sawari.pojo.DirectionResponse;
import com.sawari.sawari.pojo.SourceDestinationLatAndLong;
import com.sawari.sawari.service.GetDirectionsLIq;
import com.sawari.sawari.service.GetLatLong;
import com.sawari.sawari.service.GetSuggestionServiceForLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/get")
public class GetGeoDetailsOfLocationAndDirection {
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

    @GetMapping("/src_dest/direction")
    public ResponseEntity<?>getDirectionWithGeometry(@RequestBody SourceDestinationLatAndLong body){

    }
}
