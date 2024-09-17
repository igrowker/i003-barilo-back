package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "destinations")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;
    private String description;
    @OneToMany(mappedBy = "destination", cascade =CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Travel> travels; //¿Necesitaremos consultar los viajes a cada destination?
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Accommodation> accommodations; //puse esto para que cada destination pueda acceder a los alojamientos
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meal> meals;  //Comidas que tiene ese destination
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transport> transports; //da acceso al listado de transportes
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> activities; //accede al listado de activiades que ofrece cada destination.
}
