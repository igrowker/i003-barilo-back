package com.igrowker.miniproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityDto {
    Long id;
    String name;
    String description;
    BigDecimal price;
    String destinationName;
}
