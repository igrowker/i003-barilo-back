package com.igrowker.miniproject.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int cantidadEstudiantes;
    @OneToMany
    private List<Usuario> estudiantes;

}
