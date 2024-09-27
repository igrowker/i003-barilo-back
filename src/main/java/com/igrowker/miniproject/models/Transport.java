package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import com.igrowker.miniproject.utils.TransportCategory;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transports")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransportCategory transportCategory;
    @Column(nullable = false)
    private String companyName;
}