package com.igrowker.miniproject.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Crowdfunding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal montoObjetivo;
    private BigDecimal montoRecaudado;
    private LocalDate fechaCierre;
    @ManyToOne
    private Usuario usuario;
}