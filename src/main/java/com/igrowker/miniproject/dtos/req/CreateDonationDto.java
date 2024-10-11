package com.igrowker.miniproject.dtos.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDonationDto {
    private String name;
    private String lastName;
    private String email;
    private String paymentMethod;
    private String cbu;
    private String cvu;
    private BigDecimal amount;
}