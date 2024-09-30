package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.dtos.TravelLogDto;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.models.Travel;
import com.igrowker.miniproject.models.TravelLog;
import com.igrowker.miniproject.models.User;
import com.igrowker.miniproject.models.enums.LogType;
import com.igrowker.miniproject.models.enums.TravelEntity;
import com.igrowker.miniproject.repositories.TravelLogRepository;
import com.igrowker.miniproject.repositories.TravelRepository;
import com.igrowker.miniproject.repositories.UserRepository;
import com.igrowker.miniproject.services.interfaces.TravelLogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelLogServiceImpl implements TravelLogService {

    private final TravelLogRepository travelLogRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;

    @Autowired
    public TravelLogServiceImpl(TravelLogRepository travelLogRepository, ModelMapper modelMapper, UserRepository userRepository, TravelRepository travelRepository) {
        this.travelLogRepository = travelLogRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
    }

    @Override
    public void registerTravelLog(LogType logType, TravelEntity travelEntityChanged, String description, Long travelId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Usuario con email "+  username + "no encontrado" + ". No es posible registrar la actividad realizada"));

        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new NotFoundException("Viaje con id "+  travelId + "no encontrado" + ". No es posible registrar la actividad realizada"));

        TravelLog travelLog = new TravelLog();
        travelLog.setLogType(logType);
        travelLog.setTravelEntityChanged(travelEntityChanged);
        travelLog.setDescription(description);
        travelLog.setUser(user);
        travelLog.setTravel(travel);
        travelLogRepository.save(travelLog);
    }

    @Override
    public Page<List<TravelLogDto>> getTravelLogsByTravelId(Long travelId, Pageable pageable) {
        Page<TravelLog> travelLogs = travelLogRepository.findByTravelId(travelId, pageable);
        return travelLogs.map(travelLog -> {
            TravelLogDto travelLogDto = modelMapper.map(travelLog, TravelLogDto.class);
            return List.of(travelLogDto);
        });
    }
}