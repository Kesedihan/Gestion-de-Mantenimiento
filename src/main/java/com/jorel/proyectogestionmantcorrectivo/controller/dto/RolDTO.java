package com.jorel.proyectogestionmantcorrectivo.controller.dto;


import com.jorel.proyectogestionmantcorrectivo.entity.NombreRol;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO utilizado para manejar los roles.
 *
 * @author Jorel
 * @version 1.0
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolDTO {

    private Integer idRol;

    @NotNull(message = "El nombre del rol no puede ser nulo.")
    private NombreRol nombre;

}
