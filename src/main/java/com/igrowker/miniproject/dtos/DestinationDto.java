package com.igrowker.miniproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DestinationDto {
    private Long id;
    private String name;
    private String city;
    private List<AccommodationDto> accommodations;
    private List<ActivityDto> activities;
    private List<TransportDto> transports;
    private List<MealDto> meals;
}
