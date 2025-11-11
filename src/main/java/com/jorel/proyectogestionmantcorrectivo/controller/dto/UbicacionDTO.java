package com.jorel.proyectogestionmantcorrectivo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UbicacionDTO {
    private Long idUbicacion;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}
