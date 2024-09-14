package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "destino_id")
    private String destino;
    private BigDecimal costoTotal;
    private BigDecimal costoPorEstudiante;
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
}