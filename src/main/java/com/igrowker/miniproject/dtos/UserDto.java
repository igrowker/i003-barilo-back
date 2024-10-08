package com.igrowker.miniproject.dtos;

import com.igrowker.miniproject.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private BigDecimal pendingBalance;
    private Image image;
}
