package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.TravelLogDto;
import com.igrowker.miniproject.services.interfaces.TravelLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travelLogs")
@Tag(name = "TravelLog", description = "API para operaciones de registros de viaje")
public class TravelLogController {

    private final TravelLogService travelLogService;

    @Autowired
    public TravelLogController(TravelLogService travelLogService) {
        this.travelLogService = travelLogService;
    }

    @Operation(summary = "Obtener los logs de viaje por el id del viaje")
    @GetMapping("/{travelId}")
    public ResponseEntity<Page<List<TravelLogDto>>> getTravelLogsByTravelId(@PathVariable("travelId") Long travelId, Pageable pageable) {
        Page<List<TravelLogDto>> travelLogs = travelLogService.getTravelLogsByTravelId(travelId, pageable);
        return ResponseEntity.ok().body(travelLogs);
    }
}