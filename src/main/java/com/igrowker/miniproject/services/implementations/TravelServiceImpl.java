package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.auth.service.AuthService;
import com.igrowker.miniproject.dtos.*;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.mappers.TravelMapper;
import com.igrowker.miniproject.models.*;
import com.igrowker.miniproject.models.enums.LogType;
import com.igrowker.miniproject.models.enums.TravelEntity;
import com.igrowker.miniproject.repositories.*;
import com.igrowker.miniproject.services.interfaces.TravelLogService;
import com.igrowker.miniproject.services.interfaces.TravelService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    private final GroupRepository groupRepository;
    private final TravelLogService travelLogService;

    public TravelServiceImpl(TravelRepository travelRepository, TravelMapper travelMapper, AuthService authService,
                             DestinationRepository destinationRepository, ActivityRepository activityRepository,
                             AccommodationRepository accommodationRepository, TransportRepository transportRepository,
                             MealRepository mealRepository, UserRepository userRepository, GroupRepository groupRepository, TravelLogService travelLogService){
        this.travelRepository = travelRepository;
        this.travelMapper = travelMapper;
        this.authService = authService;
        this.destinationRepository = destinationRepository;
        this.activityRepository = activityRepository;
        this.accommodationRepository = accommodationRepository;
        this.transportRepository = transportRepository;
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.travelLogService = travelLogService;
    }

    private BigDecimal calculateCosts(TravelDto travelDto, Group group) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        long days = ChronoUnit.DAYS.between(travelDto.getStartDate(), travelDto.getEndDate());

        for (AccommodationDto accommodationDto : travelDto.getAccommodations()) {
            BigDecimal accommodationDay = accommodationDto.getPrice().multiply(BigDecimal.valueOf(days));
            totalPrice = totalPrice.add(accommodationDay.multiply(BigDecimal.valueOf(group.getStudentsQuantity())));
        }

        for (ActivityDto activityDto : travelDto.getActivities()) {
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
    public TravelDto saveTravel(TravelDto travelDto) {
        // Verificar que el grupo exista
        Group group = groupRepository.findById(travelDto.getGroupId())
                .orElseThrow(()-> new NotFoundException("Grupo no encontrado!"));

        // Verificar que el destino exista
        Destination destination = destinationRepository.findById(travelDto.getDestinationId())
                .orElseThrow(()-> new NotFoundException("Destino no encontrado!"));

        // Obtener totalPrecio
        BigDecimal totalPrice = calculateCosts(travelDto, group);

        // Obtener la cantidad de estudiantes del grupo
        BigDecimal studentQuantity = BigDecimal.valueOf(group.getStudentsQuantity());

        // Calcular costo por estudiante
        BigDecimal costPerStudent = totalPrice.divide((studentQuantity), 2, RoundingMode.HALF_UP);

        // Agregar el costo por estudiante al saldo pendiente de cada estudiante del grupo
        List<User> users = group.getUsers();
        for (User user: users){
            // Verificar si el saldo pendiente es null; si es null, inicializarlo a BigDecimal.ZERO
            BigDecimal pendingBalance = user.getPendingBalance() != null ? user.getPendingBalance() : BigDecimal.ZERO;

            // Sumar el costPerStudent al saldo pendiente actual
            BigDecimal nuevoSaldoPendiente = pendingBalance.add(costPerStudent);

            // Actualizar el saldo pendiente del usuario
            user.setPendingBalance(nuevoSaldoPendiente);

            // Guardar el usuario actualizado
            userRepository.save(user);
        }

        // Pasar los valores al travelDto
        travelDto.setGroupId(group.getId());
        travelDto.setDestinationId(destination.getId());
        travelDto.setTotalPrice(totalPrice);
        travelDto.setCostPerStudent(costPerStudent);

        Travel travel = travelMapper.toTravel(travelDto); // Mappear el DTO a la entidad

        Travel savedTravel = travelRepository.save(travel); // Guardar el viaje
        
        travelLogService.registerTravelLog(LogType.NEW, TravelEntity.TRAVEL, "Se creo un nuevo viaje", savedTravel.getId()); // Registrar el log del viaje

        return travelMapper.toTravelDto(travelRepository.save(travel)); // Guardar el viaje
    }

    @Override
    public List<TravelDto> getTravel(HttpHeaders headers) {
        Long userId = authService.getIdByLoguedUser(headers);
        List<Travel> travel = travelRepository.findByUserId(userId);

        return travelMapper.travelDtos(travel);
    }

    @Override
    public TravelDto updateTravel(Long id, TravelDto travelDto) {
        // Obtener el grupo asociado al viaje
        Group group = travelRepository.findByGroupByTravelId(id);

        // Obtener el viaje existente o lanzar excepción si no se encuentra
        Travel travel = travelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaje no encontrado!"));

        // Actualizar las listas de relaciones (comidas, transportes, alojamientos y actividades)
        updateMeals(travel, travelDto.getMeals());
        updateTransports(travel, travelDto.getTransports());
        updateAccommodations(travel, travelDto.getAccommodations());
        updateActivities(travel, travelDto.getActivities());

        // Obtener el precio total calculado
        BigDecimal totalPrice = calculateCosts(travelDto, group);

        // Obtener la cantidad de estudiantes del grupo
        BigDecimal studentQuantity = BigDecimal.valueOf(group.getStudentsQuantity());

        // Calcular el nuevo costo por estudiante
        BigDecimal newCostPerStudent = totalPrice.divide(studentQuantity, 2, RoundingMode.HALF_UP);

        // Obtener el costo por estudiante existente en el viaje actual
        BigDecimal existingCostPerStudent = travel.getCostPerStudent() != null ? travel.getCostPerStudent() : BigDecimal.ZERO;

        // Calcular el costo adicional que se agregará al saldo pendiente
        BigDecimal additionalCost = newCostPerStudent.subtract(existingCostPerStudent);

        // Actualizar el saldo pendiente de cada estudiante sumando el costo adicional
        List<User> users = group.getUsers();
        for (User user : users) {
            BigDecimal pendingBalance = user.getPendingBalance() != null ? user.getPendingBalance() : BigDecimal.ZERO;
            user.setPendingBalance(pendingBalance.add(additionalCost));  // Sumar el costo adicional al saldo pendiente existente
            userRepository.save(user);  // Guardar el usuario actualizado
        }

        // Actualizar los precios en la entidad Travel
        travel.setTotalPrice(totalPrice);
        travel.setCostPerStudent(newCostPerStudent);

        // Actualizar las fechas (startDate y endDate)
        travel.setStartDate(travelDto.getStartDate());
        travel.setEndDate(travelDto.getEndDate());

        // Actualizar el destino si ha cambiado el destinationId
        if (!travel.getDestination().getId().equals(travelDto.getDestinationId())) {
            Destination newDestination = destinationRepository.findById(travelDto.getDestinationId())
                    .orElseThrow(() -> new NotFoundException("Destino no encontrado!"));
            travel.setDestination(newDestination);
        }

        // Guardar y retornar el Travel actualizado
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

    private void updateActivities(Travel travel, List<ActivityDto> newActivities) {
        List<Activity> currentActivities = travel.getActivities();
        currentActivities.clear(); // Limpiamos la lista actual de Activities
        for (ActivityDto activityDto : newActivities) {
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
                .orElseThrow(() -> new NotFoundException("Viaje no encontrado!"));

        // Obtener el costo por estudiante del viaje que se va a eliminar
        BigDecimal costPerStudent = travel.getCostPerStudent();

        // Eliminar del viaje todas las relaciones (meals, transports, accommodations, activities)
        travel.getMeals().clear();
        travel.getTransports().clear();
        travel.getAccommodations().clear();
        travel.getActivities().clear();

        // Actualizar el saldo pendiente de cada estudiante restando el costo por estudiante del viaje eliminado
        List<User> users = group.getUsers();
        for (User user : users) {
            BigDecimal pendingBalance = user.getPendingBalance() != null ? user.getPendingBalance() : BigDecimal.ZERO;

            // Restar el costPerStudent solo si el saldo pendiente actual es mayor o igual al costo del viaje
            if (pendingBalance.compareTo(costPerStudent) >= 0) {
                user.setPendingBalance(pendingBalance.subtract(costPerStudent));  // Restar el costo del viaje del saldo pendiente
            } else {
                user.setPendingBalance(BigDecimal.ZERO);  // Evitar que el saldo pendiente sea negativo
            }

            userRepository.save(user);  // Guardar el usuario actualizado
        }

        // Eliminar el viaje de la base de datos
        travelRepository.delete(travel);
    }

}
