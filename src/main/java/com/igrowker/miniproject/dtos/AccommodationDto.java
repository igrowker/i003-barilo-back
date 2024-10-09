package com.igrowker.miniproject.dtos;

import java.math.BigDecimal;

import com.igrowker.miniproject.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String type;
    private Image image;
}