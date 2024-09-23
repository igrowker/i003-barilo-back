package com.igrowker.miniproject.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igrowker.miniproject.dtos.AccommodationDto;
import com.igrowker.miniproject.dtos.filters.AccommodationFilterDto;
import com.igrowker.miniproject.services.interfaces.AccommodationService;
import com.igrowker.miniproject.utils.BigDecimalValidator;
import com.igrowker.miniproject.utils.Response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accommodations")
@RequiredArgsConstructor
@Tag(name = "Accommodation", description = "Accommodation API")
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final BigDecimalValidator bigDecimalValidator;

    @Operation(summary = "Get all filtered accommodations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodations found"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<PagedModel<AccommodationDto>>> getAllAccommodations(
            @Parameter(description = "Accommodation name") @RequestParam(required = false) String name,
            @Parameter(description = "Accommodation price") @RequestParam(required = false) String price,
            @Parameter(description = "Accommodation type") @RequestParam(required = false) String type,
            @Parameter(description = "Destination ID") @RequestParam(required = false) Long destinationId,
            @Parameter(description = "Destination name") @RequestParam(required = false) String destinationName,
            @PageableDefault(size = 10) Pageable pageable) {
       
        AccommodationFilterDto filter = new AccommodationFilterDto();
        filter.setName(name);
        filter.setPrice(bigDecimalValidator.validateAndParse(price, "price"));
        filter.setType(type);
        filter.setDestinationId(destinationId);
        filter.setDestinationName(destinationName);
        Page<AccommodationDto> accommodations = accommodationService.getAllAccommodations(filter, pageable);
        PagedModel<AccommodationDto> pagedModel = new PagedModel<>(accommodations);
        return ResponseEntity.ok(new SuccessResponse<>(pagedModel, HttpStatus.OK));
    }

    @Operation(summary = "Get an accommodation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation found"),
            @ApiResponse(responseCode = "404", description = "Accommodation not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<AccommodationDto>> getAccommodationById(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessResponse<>(accommodationService.getAccommodationById(id), HttpStatus.OK));
    }
}
