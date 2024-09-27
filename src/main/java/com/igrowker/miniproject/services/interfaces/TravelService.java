package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.TravelDto;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface TravelService {

    public TravelDto saveTravel(TravelDto travelDto);

    public List<TravelDto> getTravel(HttpHeaders headers);

    public TravelDto updateTravel(Long id, TravelDto travelDto);

    public void deleteTravel(Long id);

}
