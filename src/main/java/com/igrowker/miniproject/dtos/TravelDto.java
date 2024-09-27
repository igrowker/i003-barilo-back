package com.igrowker.miniproject.dtos;

import com.igrowker.miniproject.models.Accommodation;
import com.igrowker.miniproject.models.Activity;
import com.igrowker.miniproject.models.Meal;
import com.igrowker.miniproject.models.Transport;
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
    //private String destinationName;
    private BigDecimal totalPrice;
    private BigDecimal costPerStudent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long groupId;
    private List<AccommodationDto> accommodations;
    private List<ActivityDTO> activities;
    private List<TransportDto> transports;
    private List<MealDto> meals;



}
