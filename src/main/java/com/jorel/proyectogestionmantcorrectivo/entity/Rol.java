package com.jorel.proyectogestionmantcorrectivo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entidad que representa un rol de usuario dentro del sistema.
 * Roles disponibles: ADMINISTRADOR, TECNICO, TRABAJADOR.
 *
 * @author Jorel
 * @version 1.0
 *
 */

@Data
@AllArgsConstructor         // Genera el constructor con los paremetros
@NoArgsConstructor          // se encarga de crear un constructor público sin argumentos (constructor vacio)
@Builder                    // Con esto, lombok implementa el patron de diseño Builder para construir objetos de esta clase
@Entity
@Table(name = "rol")
public class Rol {

    /** Identificador único del rol */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    /** Nombre del rol */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private NombreRol nombre;  // Se usa Enum en lugar de String



    // Constructor con enumeración
    public Rol(NombreRol nombre) {this.nombre = nombre;}

}
