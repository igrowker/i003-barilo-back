package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Alojamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private BigDecimal precio;
    private String tipo;
    @ManyToOne
    @JoinColumn(name = "destino_id")
    private Destino destino;
}
