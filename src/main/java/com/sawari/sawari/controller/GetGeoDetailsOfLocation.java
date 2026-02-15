package com.sawari.sawari.controller;
import com.sawari.sawari.service.GetLatLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/get")
public class GetGeoDetailsOfLocation {
    @Autowired
    private GetLatLong getLatLong;
    @GetMapping("/location/{location}")
    public ResponseEntity<String> GetDetailsFromLocationIq(@PathVariable String location){
        return getLatLong.GetLatAndLongForLocation(location);
    }
}
