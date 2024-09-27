package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.DestinationDto;
import com.igrowker.miniproject.dtos.TravelDto;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface TravelService {

    public DestinationDto getOptionsByDestinationById(Long destinationId);

    public DestinationDto getOptionsByDestinationByName(String name);

    public TravelDto saveTravel(TravelDto travelDto, HttpHeaders headers);

    public TravelDto getTravel(HttpHeaders headers);

    public TravelDto updateTravel(Long id, TravelDto travelDto);

    public void deleteTravel(Long id);

}
