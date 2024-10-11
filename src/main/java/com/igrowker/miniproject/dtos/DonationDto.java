package com.igrowker.miniproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationDto {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate date;
}
