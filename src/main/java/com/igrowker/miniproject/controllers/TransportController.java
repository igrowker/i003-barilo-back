package com.igrowker.miniproject.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igrowker.miniproject.dtos.TransportDto;
import com.igrowker.miniproject.dtos.filters.TransportFilterDto;
import com.igrowker.miniproject.dtos.req.CreateTransportDto;
import com.igrowker.miniproject.services.interfaces.TransportService;
import com.igrowker.miniproject.utils.BigDecimalValidator;
import com.igrowker.miniproject.utils.TransportCategory;
import com.igrowker.miniproject.utils.Response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transports")
@Tag(name = "Transport", description = "Transport API")
@RequiredArgsConstructor
public class TransportController {

        private final TransportService transportService;
        private final BigDecimalValidator bigDecimalValidator;

        @Operation(summary = "Get all filtered transports")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Transports found"),
                        @ApiResponse(responseCode = "400", description = "Invalid parameters"),
        })
        @GetMapping
        public ResponseEntity<SuccessResponse<PagedModel<TransportDto>>> getAllTransports(
                        @Parameter(description = "Destination ID") @RequestParam(required = false) Long destinationId,
                        @Parameter(description = "Transport name") @RequestParam(required = false) String name,
                        @Parameter(description = "Transport price") @RequestParam(required = false) String price,
                        @Parameter(description = "Destination name") @RequestParam(required = false) String destinationName,
                        @Parameter(description = "Transport type" ) @RequestParam(required = false) TransportCategory transportCategory,
                        @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable) {

                TransportFilterDto transportFilterDto = new TransportFilterDto(
                                name, bigDecimalValidator.validateAndParse(price, "price"),
                                destinationId, destinationName, transportCategory);

                Page<TransportDto> transports = transportService.getAllTransports(transportFilterDto, pageable);
                PagedModel<TransportDto> pagedModel = new PagedModel<>(transports);
                return ResponseEntity.ok(new SuccessResponse<>(pagedModel, HttpStatus.OK));

        }

        @Operation(summary = "Get a transport by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Transport found"),
                        @ApiResponse(responseCode = "404", description = "Transport not found"),
        })
        @GetMapping("/{id}")
        public ResponseEntity<SuccessResponse<TransportDto>> getTransportById(@PathVariable Long id) {
                return ResponseEntity.ok(new SuccessResponse<>(transportService.getTransportById(id), HttpStatus.OK));
        }

        @Operation(summary = "Create a new transport")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Transport created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid parameters"),
        })
        @PostMapping
        public ResponseEntity<SuccessResponse<TransportDto>> createTransport(
                        @RequestBody CreateTransportDto transportDto) {
                return ResponseEntity
                                .ok(new SuccessResponse<>(transportService.createTransport(transportDto),
                                                HttpStatus.CREATED));
        }

        @Operation(summary = "Update a transport by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Transport updated successfully"),
                        @ApiResponse(responseCode = "404", description = "Transport not found"),
                        @ApiResponse(responseCode = "400", description = "Invalid parameters"),
        })
        @PutMapping("/{id}")
        public ResponseEntity<SuccessResponse<TransportDto>> updateTransport(@PathVariable Long id,
                        @RequestBody TransportDto transportDto) {
                return ResponseEntity
                                .ok(new SuccessResponse<>(transportService.updateTransport(id, transportDto),
                                                HttpStatus.OK));
        }

        @Operation(summary = "Delete a transport by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Transport deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Transport not found"),
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<SuccessResponse<Void>> deleteTransport(@PathVariable Long id) {
                transportService.deleteTransport(id);
                return ResponseEntity.noContent().build();
        }
}
