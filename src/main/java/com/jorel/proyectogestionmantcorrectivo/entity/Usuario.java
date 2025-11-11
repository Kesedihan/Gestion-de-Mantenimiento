package com.jorel.proyectogestionmantcorrectivo.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa a un usuario dentro del sistema.
 * Puede tener distintos multiples roles.
 *
 * @author Jorel
 * @version 1.0
 *
 */

@Data
@AllArgsConstructor     // Genera el constructor con los paremetros
@NoArgsConstructor      // Genera el constructor sin los parametros
@Builder                // Con esto, lombok implementa el patron de diseño Builder para construir objetos de esta clase
@Entity
@Table(name = "usuario")
public class Usuario {

    /** Identificador único del usuario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;


    /** Nombre del usuario */
    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;


    /** Apellido del usuario */
    @NotBlank
    @Column(nullable = false, length = 100)
    private String apellido;


    /** Corro electronico del usuario (unico) */
    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String correo;


    /** Contraseña cifrada del usuario */
    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")      // Validacion para el tamaño de la contraseña
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).+$", message = "Debe contener letras y al menos un número")       //  Validacion para la contraseña
    @Column(nullable = false)
    private String contrasena;

    /** Número de teléfono opcional del usuario */
    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "El número debe contener solo dígitos (10 caracteres)")      // Validacion para el numero telefonico
    @Column(length = 10)
    private String celular;


    /** Estado del usuario (Activo o inactivo) */
    @Column(nullable = false)
    private Boolean activo = true;


    /** Descripcion del cargo del usuario */
    @Column(name = "descripcion_cargo", length = 255)
    private String descripcionCargo;


    // Relación N:M con Rol
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Rol> roles = new HashSet<>();

}
