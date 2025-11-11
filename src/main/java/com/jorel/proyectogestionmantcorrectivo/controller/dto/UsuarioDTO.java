package com.jorel.proyectogestionmantcorrectivo.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


/**
 * DTO utilizado para manejar los datos de un usuario.
 *
 * Valida que:
 * - El nombre no esté vacío.
 * - El correo tenga formato válido.
 * - La contraseña tenga al menos 8 caracteres, incluya letras y números.
 * - El número de celular tenga 10 digitos
 *
 * @author Jorel
 * @version 1.0
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private Long idUsuario;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 100 caracteres.")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Debe proporcionar un correo válido.")
    private String correo;

    @NotBlank(message = "La contrseña es obligatoria")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$", message = "Debe contener letras, al menos un número y minimo 8 caracteres")
    private String contrasena;

    @NotBlank(message = "El número de celular es obligatorio.")
    @Pattern(regexp = "^[0-9]{10}$", message = "El celular debe tener 10 dígitos.")
    private String celular;

    private Boolean activo;

    @Size(max = 255, message = "La descripción del cargo no puede superar los 255 caracteres.")
    private String descripcionCargo;

    // Relación con roles
    private Set<String> roles;



}
