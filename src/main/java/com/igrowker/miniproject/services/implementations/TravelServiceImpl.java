package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.auth.service.AuthService;
import com.igrowker.miniproject.dtos.*;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.mappers.TravelMapper;
import com.igrowker.miniproject.models.*;
import com.igrowker.miniproject.repositories.*;
import com.igrowker.miniproject.services.interfaces.TravelService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelServiceImpl implements TravelService {

    private final TravelRepository travelRepository;
    private final TravelMapper travelMapper;
    private final AuthService authService;
    private final DestinationRepository destinationRepository;
    private final ActivityRepository activityRepository;
    private final AccommodationRepository accommodationRepository;
    private final TransportRepository transportRepository;
    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private static final ModelMapper modelMapper = new ModelMapper();
    public TravelServiceImpl(TravelRepository travelRepository, TravelMapper travelMapper, AuthService authService,
                             DestinationRepository destinationRepository, ActivityRepository activityRepository,
                             AccommodationRepository accommodationRepository, TransportRepository transportRepository,
                             MealRepository mealRepository, UserRepository userRepository){
        this.travelRepository = travelRepository;
        this.travelMapper = travelMapper;
        this.authService = authService;
        this.destinationRepository = destinationRepository;
        this.activityRepository = activityRepository;
        this.accommodationRepository = accommodationRepository;
        this.transportRepository = transportRepository;
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DestinationDto getOptionsByDestinationById(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(()-> new NotFoundException("Destino no encontrado!"));
        List<TransportDto> transports = transportRepository.findAllByDestinationId(destination.getId()).stream()
                .map(transport -> modelMapper.map(transport, TransportDto.class)).collect(Collectors.toList());
        List<AccommodationDto> accommodations = accommodationRepository.findAllByDestinationId(destination.getId()).stream()
                .map(accommodation -> modelMapper.map(accommodation, AccommodationDto.class)).collect(Collectors.toList());
        List<MealDto> meals = mealRepository.findAllByDestinationId(destination.getId()).stream()
                .map(meal -> modelMapper.map(meal, MealDto.class)).collect(Collectors.toList());
        List<ActivityDTO> activities = activityRepository.findAllByDestinationId(destination.getId()).stream()
                .map(activity -> modelMapper.map(activity, ActivityDTO.class)).collect(Collectors.toList());

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
    public DestinationDto getOptionsByDestinationByName(String name) {
        Destination destination = destinationRepository.findByName(name)
                .orElseThrow(()-> new NotFoundException("Destino no encontrado"));
        List<TransportDto> transports = transportRepository.findAllByDestinationId(destination.getId()).stream()
                .map(transport -> modelMapper.map(transport, TransportDto.class)).collect(Collectors.toList());
        List<AccommodationDto> accommodations = accommodationRepository.findAllByDestinationId(destination.getId()).stream()
                .map(accommodation -> modelMapper.map(accommodation, AccommodationDto.class)).collect(Collectors.toList());
        List<MealDto> meals = mealRepository.findAllByDestinationId(destination.getId()).stream()
                .map(meal -> modelMapper.map(meal, MealDto.class)).collect(Collectors.toList());
        List<ActivityDTO> activities = activityRepository.findAllByDestinationId(destination.getId()).stream()
                .map(activity -> modelMapper.map(activity, ActivityDTO.class)).collect(Collectors.toList());

        return DestinationDto.builder()
                .id(destination.getId())
                .name(destination.getName())
                //.description(destination.getDescription())
                .city(destination.getCity())
                .activities(activities)
                .accommodations(accommodations)
                .transports(transports)
                .meals(meals).build();
    }
    private BigDecimal calcularCostos(TravelDto travelDto, Group group) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        long days = ChronoUnit.DAYS.between(travelDto.getStartDate(), travelDto.getEndDate());

        for (AccommodationDto accommodationDto : travelDto.getAccommodations()) {
            BigDecimal accommodationDay = accommodationDto.getPrice().multiply(BigDecimal.valueOf(days));
            totalPrice = totalPrice.add(accommodationDay.multiply(BigDecimal.valueOf(group.getStudentsQuantity())));
        }

        for (ActivityDTO activityDto : travelDto.getActivities()) {
            totalPrice = totalPrice.add(activityDto.getPrice().multiply(BigDecimal.valueOf(group.getStudentsQuantity())));
        }

        for (TransportDto transportDto : travelDto.getTransports()) {
            BigDecimal totalPriceTransport = transportDto.getPrice().multiply(BigDecimal.valueOf(group.getStudentsQuantity()));
            totalPrice = totalPrice.add(totalPriceTransport);
        }

        BigDecimal totalPriceMeal = BigDecimal.ZERO;
        for (MealDto mealDto : travelDto.getMeals()) {
            totalPriceMeal = totalPriceMeal.add(mealDto.getPrice().multiply(BigDecimal.valueOf(days)));
            totalPrice = totalPrice.add(totalPriceMeal.multiply(BigDecimal.valueOf(group.getStudentsQuantity())));
        }

        return totalPrice;
    }


    @Override
    public TravelDto saveTravel(TravelDto travelDto, HttpHeaders headers) {
        Long userId = authService.getIdByLoguedUser(headers); //Obtener el id del usuario logueado

        Group group = travelRepository.findByGroupByUserId(userId);// Obtener el grupo que creo el usuario

        if (group.getId()==null) //Verificar que el grupo exista
            throw new NotFoundException("Grupo no encontrado!");

        // Verificar que el destino exista
        Destination destination = destinationRepository.findById(travelDto.getDestinationId())
                .orElseThrow(()-> new NotFoundException("Destino no encontrado!"));

        // Obtener totalPrecio
        BigDecimal totalPrice = calcularCostos(travelDto, group);

        // Obtener la cantidad de estudiantes del grupo
        BigDecimal studentQuantity = BigDecimal.valueOf(group.getStudentsQuantity());

        // Calcular costo por estudiante
        BigDecimal costPerStudent = totalPrice.divide((studentQuantity), 2, RoundingMode.HALF_UP);

        // Agregar el costo por estudiante al saldo pendiente de cada estudiante del grupo
        List<User> users = group.getUsers();
        for (User user: users){
            user.setPendingBalance(costPerStudent);
            userRepository.save(user);
        }

        // Pasar los valores al travelDto
        travelDto.setGroupId(group.getId());
        travelDto.setDestinationId(destination.getId());
        travelDto.setTotalPrice(totalPrice);
        travelDto.setCostPerStudent(costPerStudent);

        Travel travel = travelMapper.toTravel(travelDto); // Mappear el DTO a la entidad
        return travelMapper.toTravelDto(travelRepository.save(travel)); // Guardar el viaje
    }

    @Override
    public TravelDto getTravel(HttpHeaders headers) {
        Long userId = authService.getIdByLoguedUser(headers);
        Travel travel = travelRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Viaje no encontrado"));

        // Convertimos la entidad Travel a DTO para devolverla
        return travelMapper.toTravelDto(travel);
    }

    @Override
    public TravelDto updateTravel(Long id, TravelDto travelDto) {
        Group group = travelRepository.findByGroupByTravelId(id);

        Travel travel = travelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaje no encontrado!"));

        // Actualizar listas de relaciones (comidas, transportes, alojamientos y actividades)
        updateMeals(travel, travelDto.getMeals());
        updateTransports(travel, travelDto.getTransports());
        updateAccommodations(travel, travelDto.getAccommodations());
        updateActivities(travel, travelDto.getActivities());

        // Obtener totalPrice
        BigDecimal totalPrice = calcularCostos(travelDto, group);

        // Obtener la cantidad de estudiantes del grupo
        BigDecimal studentQuantity = BigDecimal.valueOf(group.getStudentsQuantity());

        // Calcular costo por estudiante
        BigDecimal costPerStudent = totalPrice.divide(studentQuantity, 2, RoundingMode.HALF_UP);

        // Actualizar el costo por estudiante en el saldo pendiente de cada estudiante
        List<User> users = group.getUsers();
        for (User user : users) {
            user.setPendingBalance(costPerStudent);
            userRepository.save(user);
        }

        // Actualizar precios en la entidad Travel
        travel.setTotalPrice(totalPrice);
        travel.setCostPerStudent(costPerStudent);

        // Actualizar fechas (startDate y endDate)
        travel.setStartDate(travelDto.getStartDate());
        travel.setEndDate(travelDto.getEndDate());

        // Actualizar el destino si el destinationId ha cambiado
        if (!travel.getDestination().getId().equals(travelDto.getDestinationId())) {
            Destination newDestination = destinationRepository.findById(travelDto.getDestinationId())
                    .orElseThrow(() -> new NotFoundException("Destino no encontrado!"));
            travel.setDestination(newDestination);
        }
        // Mapear y guardar el Travel actualizado
        //travelMapper.updateTravel(travel, travelDto);
        return travelMapper.toTravelDto(travelRepository.save(travel));
    }

    private void updateMeals(Travel travel, List<MealDto> newMeals) {
        List<Meal> currentMeals = travel.getMeals();
        currentMeals.clear(); // Limpiamos la lista actual de Meals para evitar duplicados
        for (MealDto mealDto : newMeals) {
            Meal meal = mealRepository.findById(mealDto.getId())
                    .orElseThrow(() -> new NotFoundException("Meal no encontrada!"));
            currentMeals.add(meal);
        }
        travel.setMeals(currentMeals);
    }

    private void updateTransports(Travel travel, List<TransportDto> newTransports) {
        List<Transport> currentTransports = travel.getTransports();
        currentTransports.clear(); // Limpiamos la lista actual de Transports
        for (TransportDto transportDto : newTransports) {
            Transport transport = transportRepository.findById(transportDto.getId())
                    .orElseThrow(() -> new NotFoundException("Transport no encontrado!"));
            currentTransports.add(transport);
        }
        travel.setTransports(currentTransports);
    }

    private void updateAccommodations(Travel travel, List<AccommodationDto> newAccommodations) {
        List<Accommodation> currentAccommodations = travel.getAccommodations();
        currentAccommodations.clear(); // Limpiamos la lista actual de Accommodations
        for (AccommodationDto accommodationDto : newAccommodations) {
            Accommodation accommodation = accommodationRepository.findById(accommodationDto.getId())
                    .orElseThrow(() -> new NotFoundException("Accommodation no encontrada!"));
            currentAccommodations.add(accommodation);
        }
        travel.setAccommodations(currentAccommodations);
    }

    private void updateActivities(Travel travel, List<ActivityDTO> newActivities) {
        List<Activity> currentActivities = travel.getActivities();
        currentActivities.clear(); // Limpiamos la lista actual de Activities
        for (ActivityDTO activityDto : newActivities) {
            Activity activity = activityRepository.findById(activityDto.getId())
                    .orElseThrow(() -> new NotFoundException("Activity no encontrada!"));
            currentActivities.add(activity);
        }
        travel.setActivities(currentActivities);
    }

    @Override
    public void deleteTravel(Long id) {
        // Obtener el grupo
        Group group = travelRepository.findByGroupByTravelId(id);
        // Verificar que el viaje exista
        Travel travel = travelRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Viaje no encontrado!"));
        TravelDto travelDto = travelMapper.toTravelDto(travel); //Mapear la entidad a dto

        // Eliminar del viaje
        travelDto.getMeals().clear();
        travelDto.getTransports().clear();
        travelDto.getAccommodations().clear();
        travelDto.getActivities().clear();

        // Actualizar el saldo pendiente de cada estudiante a null
        List<User> users = group.getUsers();
        for (User user: users){
            user.setPendingBalance(null);
            userRepository.save(user);
        }
        travelRepository.delete(travel);
    }
}
