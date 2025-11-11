package com.jorel.proyectogestionmantcorrectivo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipoDTO {
    private Long idEquipo;
    private String nombre;
    private Boolean activo;
    private String descripcion;
    private String serial;
    private Integer cantidad;
    private LocalDate fechaAdquisicion;
    // Asociaciones (en DTO enviamos ids y nombres para conveniencia)
    private Long categoriaId;
    private String categoriaNombre;
    private Long ubicacionId;
    private String ubicacionNombre;
    // Ficha técnica resumen
    private Long fichaId;
    private String fichaMarca;
    private String fichaModelo;
}
