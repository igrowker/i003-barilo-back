package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.ActivityDto;

import java.util.List;

public interface DestinationService {
    public List<ActivityDto> getActivitiesByDestinationId(Long destinationId);
}
