package com.igrowker.miniproject.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private String message;
    private LocalDateTime SendDate;
}
