package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.dtos.ActivityDto;
import com.igrowker.miniproject.dtos.MealDto;
import com.igrowker.miniproject.repositories.ActivityRepository;
import com.igrowker.miniproject.repositories.MealRepository;
import com.igrowker.miniproject.services.interfaces.DestinationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;
    private final MealRepository mealRepository;

    public DestinationServiceImpl(ActivityRepository activityRepository, ModelMapper modelMapper, MealRepository mealRepository) {
        this.activityRepository = activityRepository;
        this.modelMapper = modelMapper;
        this.mealRepository = mealRepository;
    }

    @Override
    public List<ActivityDto> getActivitiesByDestinationId(Long destinationId) {
        return activityRepository.findAllByDestinationId(destinationId)
                .stream().map(activity -> modelMapper.map(activity, ActivityDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<MealDto> getMealsByDestinationId(Long destinationId, Pageable pageable) {
        return mealRepository.findAllByDestinationId(destinationId, pageable)
                .map(meals -> modelMapper.map(meals, MealDto.class));
    }
}

