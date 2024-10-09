package com.igrowker.miniproject.models;

import com.igrowker.miniproject.models.enums.DonationStatus;
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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = false)
    private String cbu;
    @Column(nullable = false)
    private String cvu;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private DonationStatus status; // TODO enum, devuelto, pendiente, utilizado (o algo asi)
    @ManyToOne
    @JoinColumn(name = "crowdfunding_id", nullable = false)
    private Crowdfunding crowdfunding;
}
