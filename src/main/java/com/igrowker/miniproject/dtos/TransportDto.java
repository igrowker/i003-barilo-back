package com.igrowker.miniproject.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private TransportDestinationDto destination;
}
