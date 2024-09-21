package com.igrowker.miniproject.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.math.BigDecimal;

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
import com.igrowker.miniproject.dtos.TransportFilterDto;
import com.igrowker.miniproject.dtos.req.CreateTransportDto;
import com.igrowker.miniproject.services.interfaces.ITransportService;
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

        private final ITransportService transportService;

        @Operation(summary = "Obtener todos los transportes filtrados")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Transportes encontrados"),
                        @ApiResponse(responseCode = "400", description = "Parámetros incorrectos"),
        })
        @GetMapping
        public ResponseEntity<SuccessResponse<PagedModel<TransportDto>>> getAllTransports(
                        @Parameter(description = "Nombre del transporte") @RequestParam(required = false) String name,
                        @Parameter(description = "Precio del transporte") @RequestParam(required = false) String price,
                        @Parameter(description = "ID del destino") @RequestParam(required = false) Long destinationId,
                        @Parameter(description = "Nombre del destino") @RequestParam(required = false) String destinationName,
                        @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable) {

                BigDecimal priceValue = null;
                if (price != null && !price.isEmpty()) {
                        try {
                                priceValue = new BigDecimal(price);
                        } catch (NumberFormatException e) {
                                throw new IllegalArgumentException("El precio no es válido", e);
                        }
                }
                TransportFilterDto transportFilterDto = new TransportFilterDto(name, priceValue, destinationId,
                                destinationName);
                Page<TransportDto> transports = transportService.getAllTransports(transportFilterDto, pageable);
                PagedModel<TransportDto> pagedModel = new PagedModel<>(transports);
                return ResponseEntity.ok(new SuccessResponse<>(pagedModel, HttpStatus.OK));

        }

        @Operation(summary = "Obtener un transporte por ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Transporte encontrado"),
                        @ApiResponse(responseCode = "404", description = "Transporte no encontrado"),
        })
        @GetMapping("/{id}")
        public ResponseEntity<SuccessResponse<TransportDto>> getTransportById(@PathVariable Long id) {
                return ResponseEntity.ok(new SuccessResponse<>(transportService.getTransportById(id), HttpStatus.OK));
        }

        @Operation(summary = "Crear un nuevo transporte")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Transporte creado correctamente"),
                        @ApiResponse(responseCode = "400", description = "Parámetros incorrectos"),
        })
        @PostMapping
        public ResponseEntity<SuccessResponse<TransportDto>> createTransport(
                        @RequestBody CreateTransportDto transportDto) {
                return ResponseEntity
                                .ok(new SuccessResponse<>(transportService.createTransport(transportDto),
                                                HttpStatus.CREATED));
        }

        @Operation(summary = "Actualizar un transporte por ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Transporte actualizado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Transporte no encontrado"),
                        @ApiResponse(responseCode = "400", description = "Parámetros incorrectos"),
        })
        @PutMapping("/{id}")
        public ResponseEntity<SuccessResponse<TransportDto>> updateTransport(@PathVariable Long id,
                        @RequestBody TransportDto transportDto) {
                return ResponseEntity
                                .ok(new SuccessResponse<>(transportService.updateTransport(id, transportDto),
                                                HttpStatus.OK));
        }

        @Operation(summary = "Eliminar un transporte por ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Transporte eliminado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Transporte no encontrado"),
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<SuccessResponse<Void>> deleteTransport(@PathVariable Long id) {
                transportService.deleteTransport(id);
                return ResponseEntity.noContent().build();
        }
}
