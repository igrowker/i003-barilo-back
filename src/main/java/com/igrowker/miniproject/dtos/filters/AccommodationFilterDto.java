package com.igrowker.miniproject.dtos.filters;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationFilterDto {
    private String name;
    private BigDecimal price;
    private String type;
    private Long destinationId;
    private String destinationName;
}
