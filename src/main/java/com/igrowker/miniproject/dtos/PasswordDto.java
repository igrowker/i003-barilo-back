package com.igrowker.miniproject.dtos;

import lombok.Data;

@Data
public class PasswordDto {
    private String currentPassword;
    private String newPassword;
}
