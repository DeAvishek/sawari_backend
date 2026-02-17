package com.sawari.sawari.controller;
import com.sawari.sawari.pojo.DirectionResponse;
import com.sawari.sawari.service.GetDirectionsLIq;
import com.sawari.sawari.service.GetLatLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/get")
public class GetGeoDetailsOfLocationAndDirection {
    @Autowired
    private GetLatLong getLatLong;

    @Autowired
    private GetDirectionsLIq getDirectionsLIq;
    @GetMapping("/location/{location}")
    public ResponseEntity<String> GetDetailsFromLocationIq(@PathVariable String location){
        return getLatLong.GetLatAndLongForLocation(location);
    }

    @GetMapping("/src_dest/routes")
    public  ResponseEntity<DirectionResponse> getRoutes(){
        return getDirectionsLIq.getDirections();
    }
//

}
