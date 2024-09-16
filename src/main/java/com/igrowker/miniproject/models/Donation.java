package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String paymentMethod;
    private String cbu;
    private String cvu;
    private BigDecimal amount;
    private LocalDate date;
    private String status; // TODO enum, devuelto, pendiente, utilizado (o algo asi)
    @ManyToOne
    @JoinColumn(name = "crowdfunding_id")
    private Crowdfunding crowdfunding;
}
