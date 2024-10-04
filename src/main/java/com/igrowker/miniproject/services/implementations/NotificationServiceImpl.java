package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.auth.service.AuthService;
import com.igrowker.miniproject.dtos.NotificationDto;
import com.igrowker.miniproject.models.Notification;
import com.igrowker.miniproject.models.User;
import com.igrowker.miniproject.repositories.NotificationRepository;
import com.igrowker.miniproject.repositories.UserRepository;
import com.igrowker.miniproject.services.interfaces.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    public NotificationServiceImpl(UserRepository userRepository, NotificationRepository notificationRepository,
                                   EmailService emailService, AuthService authService, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
        this.authService = authService;
        this.modelMapper = modelMapper;
    }
    @Scheduled(cron = "0 0 12 * * 5") // Ejecutar todos los viernes las 12:00
    @Override
    public void sendbalancereminders() {
        List<User> users = userRepository.findAll();

        //Obtener los usuarios con el saldo pendiente >0
        List<User> usersPendingBalance = users.stream()
                .filter(user ->user.getPendingBalance() != null && user.getPendingBalance()
                        .compareTo(BigDecimal.ZERO) > 0).toList();
        String url = "https://barilo.vercel.app";

        for (User user: usersPendingBalance){
            String message = "Tienes saldo pendiente de: " + user.getPendingBalance() + " por favor pagar para completar el viaje";
            emailService.sendSimpleMessage(user.getEmail(), message, "<div>Para pagar ingresa : <a href='" + url + "'>aquí</a></div>");
            Notification notification = Notification.builder()
                    .user(user)
                    .message(message)
                    .type("EMAIL")
                    .sendDate(LocalDateTime.now())
                    .sent(true)
                    .build();
            notificationRepository.save(notification);

        }

    }

    // Para que el usuario vea sus notificaciones
    @Override
    public Page<NotificationDto> getUserNotifications(HttpHeaders headers, Pageable pageable) {
        Long userId = authService.getIdByLoguedUser(headers);
        Page<Notification> notifications = notificationRepository.findByUserIdOrderByIdDesc(userId, pageable);
        return notifications.map(notification -> modelMapper.map(notification, NotificationDto.class));
    }
}
