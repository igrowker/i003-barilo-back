package com.igrowker.miniproject.dtos.req;

import com.igrowker.miniproject.utils.TransportCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransportDto {
    private String name;
    private BigDecimal price;
    private Long destinationId;
    @Schema(description = "Destination name", example = "BASIC or STANDARD or PREMIUM")
    private TransportCategory transportCategory;
}
