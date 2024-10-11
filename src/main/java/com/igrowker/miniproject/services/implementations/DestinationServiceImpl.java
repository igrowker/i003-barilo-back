package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.dtos.*;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.models.Destination;
import com.igrowker.miniproject.repositories.*;
import com.igrowker.miniproject.services.interfaces.DestinationService;
import com.igrowker.miniproject.services.interfaces.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final ActivityRepository activityRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;
    private final MealRepository mealRepository;
    private final DestinationRepository destinationRepository;
    private final TransportRepository transportRepository;
    private final AccommodationRepository accommodationRepository;

    public DestinationServiceImpl(ActivityRepository activityRepository, ImageService imageService, ModelMapper modelMapper, MealRepository mealRepository,
                                  DestinationRepository destinationRepository, TransportRepository transportRepository, AccommodationRepository accommodationRepository) {
        this.activityRepository = activityRepository;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
        this.mealRepository = mealRepository;
        this.destinationRepository = destinationRepository;
        this.transportRepository = transportRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<ActivityDto> getActivitiesByDestinationId(Long destinationId) {
        return activityRepository.findAllByDestinationId(destinationId)
                .stream().map(activity -> {
                    ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
                    activityDto.setImage(activity.getImageId() != null ? imageService.findByPublicId(activity.getImageId()).orElse(null) : null);
                    return activityDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<MealDto> getMealsByDestinationId(Long destinationId, Pageable pageable) {
        return mealRepository.findAllByDestinationId(destinationId, pageable)
                .map(meals -> modelMapper.map(meals, MealDto.class));
    }

    @Override
    public DestinationDto getDestinationById(Long destinationId) {
        // Obtener destino por ID
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(()-> new NotFoundException("Destino no encontrado!"));
        // Obtener todas las opciones asociadas al destino
        List<TransportDto> transports = transportRepository.findAllByDestinationId(destination.getId()).stream()
                .map(transport -> modelMapper.map(transport, TransportDto.class)).collect(Collectors.toList());

        List<AccommodationDto> accommodations = accommodationRepository.findAllByDestinationId(destination.getId()).stream()
                .map(accommodation -> modelMapper.map(accommodation, AccommodationDto.class)).collect(Collectors.toList());

        List<MealDto> meals = mealRepository.findAllByDestinationId(destination.getId()).stream()
                .map(meal -> modelMapper.map(meal, MealDto.class)).collect(Collectors.toList());

        List<ActivityDto> activities = activityRepository.findAllByDestinationId(destination.getId()).stream()
                .map(activity -> modelMapper.map(activity, ActivityDto.class)).collect(Collectors.toList());

        return DestinationDto.builder()
                .id(destination.getId())
                .name(destination.getName())
                .city(destination.getCity())
                .activities(activities)
                .accommodations(accommodations)
                .transports(transports)
                .meals(meals).build();
    }

    @Override
    public DestinationDto getDestinationByName(String name) {
        // Buscar destino por nombre
        Destination destination = destinationRepository.findByName(name)
                .orElseThrow(()-> new NotFoundException("Destino no encontrado"));
        // Obtener todas las opciones asociadas al destino
        List<TransportDto> transports = transportRepository.findAllByDestinationId(destination.getId()).stream()
                .map(transport -> modelMapper.map(transport, TransportDto.class)).collect(Collectors.toList());

        List<AccommodationDto> accommodations = accommodationRepository.findAllByDestinationId(destination.getId()).stream()
                .map(accommodation -> modelMapper.map(accommodation, AccommodationDto.class)).collect(Collectors.toList());

        List<MealDto> meals = mealRepository.findAllByDestinationId(destination.getId()).stream()
                .map(meal -> modelMapper.map(meal, MealDto.class)).collect(Collectors.toList());

        List<ActivityDto> activities = activityRepository.findAllByDestinationId(destination.getId()).stream()
                .map(activity -> modelMapper.map(activity, ActivityDto.class)).collect(Collectors.toList());

        return DestinationDto.builder()
                .id(destination.getId())
                .name(destination.getName())
                .city(destination.getCity())
                .activities(activities)
                .accommodations(accommodations)
                .transports(transports)
                .meals(meals).build();
    }

    @Override
    public Page<DestinationDto> getDestinationsByCity(String city, Pageable pageable) {
        // Buscar todos los destinos en la ciudad
        Page<Destination> destinations = destinationRepository.findAllByCity(city, pageable);

        if (destinations.isEmpty()) {
            throw new NotFoundException("No se encontraron destinos para la ciudad: " + city);
        }

        // Mapear cada destino a un DestinationDto incluyendo todas sus opciones
        return destinations.map(destination -> {
            // Obtener todas las opciones asociadas al destino
            List<TransportDto> transports = transportRepository.findAllByDestinationId(destination.getId()).stream()
                    .map(transport -> modelMapper.map(transport, TransportDto.class))
                    .collect(Collectors.toList());

            List<AccommodationDto> accommodations = accommodationRepository.findAllByDestinationId(destination.getId()).stream()
                    .map(accommodation -> modelMapper.map(accommodation, AccommodationDto.class))
                    .collect(Collectors.toList());

            List<MealDto> meals = mealRepository.findAllByDestinationId(destination.getId()).stream()
                    .map(meal -> modelMapper.map(meal, MealDto.class))
                    .collect(Collectors.toList());

            List<ActivityDto> activities = activityRepository.findAllByDestinationId(destination.getId()).stream()
                    .map(activity -> modelMapper.map(activity, ActivityDto.class))
                    .collect(Collectors.toList());

            // Crear y retornar el DestinationDto
            return DestinationDto.builder()
                    .id(destination.getId())
                    .name(destination.getName())
                    .city(destination.getCity())
                    .activities(activities)
                    .accommodations(accommodations)
                    .transports(transports)
                    .meals(meals)
                    .build();
        });
    }


}

