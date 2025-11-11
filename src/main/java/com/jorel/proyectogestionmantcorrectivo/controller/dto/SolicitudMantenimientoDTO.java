package com.jorel.proyectogestionmantcorrectivo.controller.dto;

import com.jorel.proyectogestionmantcorrectivo.entity.EstadoSolicitud;
import com.jorel.proyectogestionmantcorrectivo.entity.TipoFalla;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudMantenimientoDTO {

    private Long idSolicitud;

    @NotNull(message = "El ID del usuario solicitante es obligatorio")
    private Long idUsuario; // ID del usuario solicitante

    @NotNull(message = "El ID del equipo es obligatorio")
    private Long idEquipo;  // ID del equipo relacionado

    @NotNull(message = "El tipo de falla es obligatorio")
    private TipoFalla tipoFalla;

    @NotBlank(message = "La descripción de la falla es obligatoria")
    @Size(max = 500, message = "La descripción no debe superar los 500 caracteres")
    private String descripcionFalla;

    @PastOrPresent(message = "La fecha de solicitud no puede ser futura")
    private LocalDate fechaSolicitud;

    @NotNull(message = "El estado de la solicitud es obligatorio")
    private EstadoSolicitud estadoSolicitud;

}
