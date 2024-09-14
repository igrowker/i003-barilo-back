package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal monto;
    private LocalDate fecha;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
