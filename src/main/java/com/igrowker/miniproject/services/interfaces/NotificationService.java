package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;

public interface NotificationService {

    public void sendbalancereminders();

    public Page<NotificationDto> getUserNotifications(HttpHeaders headers, Pageable pageable);
}
