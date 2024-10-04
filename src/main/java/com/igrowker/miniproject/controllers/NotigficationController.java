package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.NotificationDto;
import com.igrowker.miniproject.services.interfaces.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "Notification API")
public class NotigficationController {

    private final NotificationService notificationService;

    public NotigficationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @Operation(summary = "Allows the user to see their notifications")
    @GetMapping
    public ResponseEntity<Page<NotificationDto>> getUserNotifications(@RequestHeader HttpHeaders headers,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(notificationService.getUserNotifications(headers, pageable), HttpStatus.OK);
    }
}
