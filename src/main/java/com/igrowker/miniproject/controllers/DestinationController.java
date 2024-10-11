package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.ActivityDto;
import com.igrowker.miniproject.dtos.DestinationDto;
import com.igrowker.miniproject.dtos.MealDto;
import com.igrowker.miniproject.services.interfaces.DestinationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
@Tag(name = "Destinations", description = "Destination API")
public class DestinationController {

    private final DestinationService destinationService;

    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @Operation(summary = "Test endpoint")
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint");
    }

    @Operation(summary = "Get all activities by destination Id")
    @GetMapping("/{destinationId}/activities")
    public ResponseEntity<List<ActivityDto>> getActivities(@PathVariable Long destinationId) {
        return ResponseEntity.ok(destinationService.getActivitiesByDestinationId(destinationId));
    }

    @Operation(summary = "Get all meals by destination Id")
    @GetMapping("/{destinationId}/meals")
    public ResponseEntity<Page<MealDto>> getMeals(@PathVariable Long destinationId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(destinationService.getMealsByDestinationId(destinationId, pageable));
    }

    @Operation(summary = "Get destination by Id")
    @GetMapping("/{destinationId}")
    public ResponseEntity<DestinationDto> getDestinationById(@PathVariable Long destinationId) {
        return ResponseEntity.ok(destinationService.getDestinationById(destinationId));
    }

    @Operation(summary = "Get destination by name")
    @GetMapping("/name")
    public ResponseEntity<DestinationDto> getDestinationsByName(@RequestParam String name) {
        return ResponseEntity.ok(destinationService.getDestinationByName(name));
    }

    @Operation(summary = "Get all destination by city")
    @GetMapping("/city")
    public ResponseEntity<Page<DestinationDto>> getDestinationsByCity(@RequestParam String city,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(destinationService.getDestinationsByCity(city, pageable));
    }

}
