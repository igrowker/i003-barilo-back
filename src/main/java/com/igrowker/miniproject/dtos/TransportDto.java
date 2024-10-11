package com.igrowker.miniproject.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.igrowker.miniproject.models.Image;
import com.igrowker.miniproject.utils.TransportCategory;

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
    private String companyName;
    private BigDecimal price;
    private String transportCategory;
    private TransportDestinationDto destination;
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private Image image;
}
