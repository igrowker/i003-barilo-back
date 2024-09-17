package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.ActivityDTO;

import java.util.List;

public interface DestinationService {
    public List<ActivityDTO> getActivitiesByDestinationId(Long destinationId);
}
