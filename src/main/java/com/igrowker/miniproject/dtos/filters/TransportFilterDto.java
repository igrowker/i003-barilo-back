package com.igrowker.miniproject.dtos.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportFilterDto {
    private String name;
    private BigDecimal price;
    private Long destinationId;
    private String destinationName;

}
