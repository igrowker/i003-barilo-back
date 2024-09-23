package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.dtos.ActivityDto;
import com.igrowker.miniproject.repositories.ActivityRepository;
import com.igrowker.miniproject.services.interfaces.DestinationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;

    public DestinationServiceImpl(ActivityRepository activityRepository, ModelMapper modelMapper) {
        this.activityRepository = activityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ActivityDto> getActivitiesByDestinationId(Long destinationId) {
        return activityRepository.findAllByDestinationId(destinationId)
                .stream().map(activity -> modelMapper.map(activity, ActivityDto.class))
                .collect(Collectors.toList());
    }
}

