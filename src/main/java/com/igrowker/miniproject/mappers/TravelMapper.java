package com.igrowker.miniproject.mappers;

import com.igrowker.miniproject.dtos.TravelDto;
import com.igrowker.miniproject.models.Travel;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelMapper {

    private static final ModelMapper modelMapper= new ModelMapper();

    public Travel toTravel(TravelDto travelDto){
        return modelMapper.map(travelDto, Travel.class);
    }

    public TravelDto toTravelDto(Travel travel){
        return modelMapper.map(travel, TravelDto.class);
    }

    public List<TravelDto> travelDtos(List<Travel> travels){
        return travels.stream().map(travel -> modelMapper.map(travel, TravelDto.class))
                .collect(Collectors.toList());
    }

    public void updateTravel(Travel travel, TravelDto travelDto){
        modelMapper.map(travel, travelDto);
    }
}
