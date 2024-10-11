package com.igrowker.miniproject.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CrowdfundingDto {
    private Long id;
    private String name;
    private BigDecimal targetAmount;
    private BigDecimal collectedAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;
}
