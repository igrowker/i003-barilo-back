package com.igrowker.miniproject.dtos;

import com.igrowker.miniproject.models.enums.LogType;
import com.igrowker.miniproject.models.enums.TravelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelLogDto {
    private Long id;
    private LocalDateTime date;
    private LogType logType;
    private TravelEntity travelEntityChanged;
    private String description;
    private Long userId;
    private Long travelId;
}