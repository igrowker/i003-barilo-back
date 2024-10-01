package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.TravelLogDto;
import com.igrowker.miniproject.models.TravelLog;
import com.igrowker.miniproject.models.enums.LogType;
import com.igrowker.miniproject.models.enums.TravelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TravelLogService {
    void registerTravelLog(LogType logType, TravelEntity travelEntityChanged, String description, Long travelId);
    Page<List<TravelLogDto>> getTravelLogsByTravelId(Long travelId, Pageable pageable);
}