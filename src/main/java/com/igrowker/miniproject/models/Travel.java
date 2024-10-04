package com.igrowker.miniproject.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private String origin;
    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costPerStudent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToMany
    @JoinTable(
            name = "travel_transports",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "transport_id")
    )
    private List<Transport> transports;

    @ManyToMany
    @JoinTable(
            name = "travel_accommodations",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "accommodation_id")
    )
    private List<Accommodation> accommodations;

    @ManyToMany
    @JoinTable(
            name = "travel_meals",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals;

    @ManyToMany
    @JoinTable(
            name = "travel_activities",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> activities;

}