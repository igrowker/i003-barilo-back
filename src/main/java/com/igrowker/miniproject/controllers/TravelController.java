package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.TravelDto;
import com.igrowker.miniproject.services.interfaces.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travels")
@Tag(name = "Travels", description = "Travel API")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService){
        this.travelService = travelService;
    }

    @Operation(summary = "Allows a coordinator user to create a trip")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('COORDINADOR')")
    public ResponseEntity<TravelDto> saveTravel(@RequestBody TravelDto travelDto){
        return new ResponseEntity<>(travelService.saveTravel(travelDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Allows a user to view their trips")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ESTUDIANTE') or hasAnyAuthority('COORDINADOR')")
    public ResponseEntity<List<TravelDto>> getTravel(@RequestHeader HttpHeaders headers){
        return new ResponseEntity<>(travelService.getTravel(headers), HttpStatus.OK);
    }

    @Operation(summary = "Allows a coordinator user to modify a trip")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COORDINADOR')")
    public ResponseEntity<TravelDto> updateTravel(@PathVariable Long id, @RequestBody TravelDto travelDto){
        return new ResponseEntity<>(travelService.updateTravel(id, travelDto), HttpStatus.OK);
    }

    @Operation(summary = "Allows a coordinator user to delete a trip")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COORDINADOR')")
    public ResponseEntity<?> deleteTravel(@PathVariable Long id){
        travelService.deleteTravel(id);
        return new ResponseEntity<>("Viaje eliminado!", HttpStatus.OK);
    }
}
