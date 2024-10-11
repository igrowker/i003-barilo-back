package com.igrowker.miniproject.models;

import com.igrowker.miniproject.models.enums.TypeClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image_entity_link")
public class ImageEntityLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "public_id")
    private Image image;

    @Column(nullable = false)
    private Long entityId;

    @Enumerated(EnumType.STRING)
    private TypeClass entityType;
}
