package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "travels")
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;
    private BigDecimal totalPrice;
    private BigDecimal costPerStudent;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}