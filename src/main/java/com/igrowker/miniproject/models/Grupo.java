package com.igrowker.miniproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int cantidadEstudiantes;

    @OneToMany(mappedBy = "grupo")
    private List<Usuario> estudiantes;

}
