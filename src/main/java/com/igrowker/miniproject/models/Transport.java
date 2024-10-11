package com.igrowker.miniproject.models;

import com.igrowker.miniproject.utils.TransportCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @Column(nullable = false)
    private String transportCategory;
    @Column(nullable = false)
    private String companyName;
    @Column
    private String imageId;
    @Column(nullable = false)
    private LocalDateTime departureDate;
    @Column
    private LocalDateTime returnDate;

    @PrePersist
    public void prePersist() {
        this.departureDate = LocalDateTime.now();
    }

    @AssertTrue(message = "La fecha de regreso debe ser posterior a la fecha de ida")
    public boolean isValidReturnDate() {
        return returnDate.isAfter(departureDate);
    }
}