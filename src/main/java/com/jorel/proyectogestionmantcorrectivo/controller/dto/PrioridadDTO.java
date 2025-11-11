package com.jorel.proyectogestionmantcorrectivo.controller.dto;

import com.jorel.proyectogestionmantcorrectivo.entity.NivelPrioridad;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrioridadDTO {

    private Long idPrioridad;

    @NotNull(message = "El nivel de prioridad es obligatorio")
    private NivelPrioridad nivel; // Enum

    @NotNull(message = "El tiempo máximo de respuesta es obligatorio")
    @Positive(message = "El tiempo máximo de respuesta debe ser un número positivo")
    private Integer tiempoMaxRespuesta;

}
