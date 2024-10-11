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
                .map(meals -> {MealDto mealDto = modelMapper.map(meals, MealDto.class);
                    mealDto.setImage(meals.getImageId() != null ? imageService.findByPublicId(meals.getImageId()).orElse(null) : null);
                    return mealDto;
                });
    }

    // Obtiene el destino y sus paquetes
    public DestinationDto getDestinationAndPackages(Destination destination){
        // Obtener todas las opciones asociadas al destino
        List<TransportDto> transports = transportRepository.findAllByDestinationId(destination.getId()).stream()
                .map(transport -> {TransportDto transportDto = modelMapper.map(transport, TransportDto.class);
                    transportDto.setImage(transport.getImageId() != null ? imageService.findByPublicId(transport.getImageId()).orElse(null) : null);
                    return transportDto;
                }).collect(Collectors.toList());

        List<AccommodationDto> accommodations = accommodationRepository.findAllByDestinationId(destination.getId()).stream()
                .map(accommodation -> {AccommodationDto accommodationDto = modelMapper.map(accommodation, AccommodationDto.class);
                    accommodationDto.setImage(accommodation.getImageId() != null ? imageService.findByPublicId(accommodation.getImageId()).orElse(null) : null);
                    return accommodationDto;
                }).collect(Collectors.toList());

        List<MealDto> meals = mealRepository.findAllByDestinationId(destination.getId()).stream()
                .map(meal -> {MealDto mealDto = modelMapper.map(meal, MealDto.class);
                    mealDto.setImage(meal.getImageId() != null ? imageService.findByPublicId(meal.getImageId()).orElse(null) : null);
                    return mealDto;
                }).collect(Collectors.toList());

        List<ActivityDto> activities = activityRepository.findAllByDestinationId(destination.getId()).stream()
                .map(activity -> {ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
                    activityDto.setImage(activity.getImageId() != null ? imageService.findByPublicId(activity.getImageId()).orElse(null) : null);
                    return activityDto;
                }).collect(Collectors.toList());
        // Mapear cada destino a un DestinationDto incluyendo todas sus opciones
        return DestinationDto.builder()
                .id(destination.getId())
                .name(destination.getName())
                .city(destination.getCity())
                .image(destination.getImageId() != null ? imageService.findByPublicId(destination.getImageId()).orElse(null) : null)
                .activities(activities)
                .accommodations(accommodations)
                .transports(transports)
                .meals(meals).build();
    }

    @Override
    public DestinationDto getDestinationById(Long destinationId) {
        // Obtener destino por ID
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(()-> new NotFoundException("Destino no encontrado!"));
        return getDestinationAndPackages(destination); // Destino y sus opciones
    }

    @Override
    public DestinationDto getDestinationByName(String name) {
        // Buscar destino por nombre
        Destination destination = destinationRepository.findByName(name)
                .orElseThrow(()-> new NotFoundException("Destino no encontrado"));
        return getDestinationAndPackages(destination); // Destino y sus opciones
    }

    @Override
    public Page<DestinationDto> getDestinationsByCity(String city, Pageable pageable) {
        // Buscar todos los destinos en la ciudad
        Page<Destination> destinations = destinationRepository.findAllByCity(city, pageable);

        if (destinations.isEmpty()) {
            throw new NotFoundException("No se encontraron destinos para la ciudad: " + city);
        }

        return destinations.map(this::getDestinationAndPackages); // Todo los destinos y sus opciones
    }


}

