package com.igrowker.miniproject.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destino;
    private BigDecimal costoTotal;
    private BigDecimal costoPorEstudiante;
    @ManyToOne
    private Grupo grupo;
}