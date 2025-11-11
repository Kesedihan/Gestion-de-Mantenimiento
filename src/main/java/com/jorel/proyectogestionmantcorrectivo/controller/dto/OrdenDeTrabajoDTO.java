package com.jorel.proyectogestionmantcorrectivo.controller.dto;


import com.jorel.proyectogestionmantcorrectivo.entity.EstadoOrdenTrabajo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenDeTrabajoDTO {

    private Long idOrden;

    @NotNull(message = "El ID de la solicitud es obligatorio")
    private Long solicitudId;

    @NotNull(message = "El ID de la prioridad es obligatorio")
    private Long prioridadId;

    @NotNull(message = "El ID del técnico es obligatorio")
    private Long tecnicoId;     // Usuario asignado

    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaCierre;

    @NotNull(message = "El estado de la orden de trabajo es obligatorio")
    private EstadoOrdenTrabajo estado;

    @Size(max = 1000, message = "El diagnóstico no debe superar los 1000 caracteres")
    private String diagnostico;

    @Size(max = 1000, message = "La solución no debe superar los 1000 caracteres")
    private String solucion;
}
