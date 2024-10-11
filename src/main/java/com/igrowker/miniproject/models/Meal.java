package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "meals")
public class Meal {
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
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String type;
    @Column
    private String imageId;
}
