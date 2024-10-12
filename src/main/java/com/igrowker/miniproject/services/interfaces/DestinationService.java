package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.ActivityDto;
import com.igrowker.miniproject.dtos.DestinationDto;
import com.igrowker.miniproject.dtos.MealDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DestinationService {
    public List<ActivityDto> getActivitiesByDestinationId(Long destinationId);

    public Page<MealDto> getMealsByDestinationId(Long destinationId, Pageable pageable);

    public DestinationDto getDestinationById(Long destinationId);

    public DestinationDto getDestinationByName(String name);

    public Page<DestinationDto> getDestinationsByCity(String city, Pageable pageable);
}
