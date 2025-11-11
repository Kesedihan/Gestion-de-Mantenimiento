package com.jorel.proyectogestionmantcorrectivo.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Entidad que representa un equipo dentro del sistema.
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
@Table(name = "equipo")
public class Equipo {

    /** Identificador unico del equipo */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Long idEquipo;


    /** Nombre del equipo */
    @NotBlank
    @Column(nullable = false)
    private String nombre;


    /** Estado del equipo (Activo o no) */
    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;


    // Relaciones

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private Ubicacion ubicacion;

    // Opcionales/extendidos para inventario
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column
    @Builder.Default
    private Integer cantidad = 1;

    @Column(length = 100)
    private String serial;

    @Column(name = "fecha_adquisicion")
    private LocalDate fechaAdquisicion;


}
