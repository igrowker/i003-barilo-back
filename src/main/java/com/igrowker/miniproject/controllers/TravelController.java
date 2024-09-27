package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.DestinationDto;
import com.igrowker.miniproject.dtos.TravelDto;
import com.igrowker.miniproject.services.interfaces.TravelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travels")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService){
        this.travelService = travelService;
    }

    @GetMapping("/options/{id}")
    public ResponseEntity<DestinationDto> getDestinationById(@PathVariable Long id){
        return new ResponseEntity<>(travelService.getOptionsByDestinationById(id), HttpStatus.OK);
    }

    @GetMapping("/options")
    public ResponseEntity<DestinationDto> getDestinationsByName(@RequestParam String name){
        return new ResponseEntity<>(travelService.getOptionsByDestinationByName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TravelDto> saveTravel(@RequestBody TravelDto travelDto, @RequestHeader HttpHeaders headers){
        return new ResponseEntity<>(travelService.saveTravel(travelDto, headers), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<TravelDto> getTravel(@RequestHeader HttpHeaders headers){
        return new ResponseEntity<>(travelService.getTravel(headers), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelDto> updateTravel(@PathVariable Long id, @RequestBody TravelDto travelDto){
        return new ResponseEntity<>(travelService.updateTravel(id, travelDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTravel(@PathVariable Long id){
        travelService.deleteTravel(id);
        return new ResponseEntity<>("Viaje eliminado!", HttpStatus.OK);
    }
}
