package com.igrowker.miniproject.models;

import com.igrowker.miniproject.models.enums.LogType;
import com.igrowker.miniproject.models.enums.TravelEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="travel_logs")
public class TravelLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    @Enumerated(EnumType.STRING)
    private LogType logType;
    @Enumerated(EnumType.STRING)
    private TravelEntity travelEntityChanged;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Travel travel;
}