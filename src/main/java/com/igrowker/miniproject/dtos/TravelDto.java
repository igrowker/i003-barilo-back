package com.igrowker.miniproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelDto {
    private Long id;
    private Long destinationId;
    private String origin;
    private BigDecimal totalPrice;
    private BigDecimal costPerStudent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long groupId;
    private List<AccommodationDto> accommodations;
    private List<ActivityDto> activities;
    private List<TransportDto> transports;
    private List<MealDto> meals;



}
