package com.igrowker.miniproject.dtos.req;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransportDto {
    private String name;
    private BigDecimal price;
    private Long destinationId;

}
