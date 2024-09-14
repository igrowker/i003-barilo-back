package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String ciudad;
    private String descripcion;
    @OneToMany(mappedBy = "destino", cascade =CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Viaje> viajes; //¿Necesitaremos consultar los viajes a cada destino?
    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alojamiento> alojamientos; //puse esto para que cada destino pueda acceder a los alojamientos
    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comida> comidas;  //Comidas que tiene ese destino
    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transporte> transportes; //da acceso al listado de transportes
    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Actividad> actividades; //accede al listado de activiades que ofrece cada destino.
}
