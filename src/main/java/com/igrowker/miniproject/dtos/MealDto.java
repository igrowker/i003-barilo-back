package com.igrowker.miniproject.dtos;

import com.igrowker.miniproject.models.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String destinationName;
    private Image image;
}
