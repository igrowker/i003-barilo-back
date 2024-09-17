package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.ActivityDTO;
import com.igrowker.miniproject.services.interfaces.DestinationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
@Tag(name = "Destination", description = "Destination API")
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

    @Operation(summary = "Get all activities by destinationName id")
    @GetMapping("/{destinationId}/activities")
    public ResponseEntity<List<ActivityDTO>> getActivities(@PathVariable Long destinationId) {
        return ResponseEntity.ok(destinationService.getActivitiesByDestinationId(destinationId));
    }

}
