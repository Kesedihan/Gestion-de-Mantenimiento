package com.jorel.proyectogestionmantcorrectivo.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa la ficha tecnica de un equipo dentro del sistema.
 * Contiene las especificaciones y caracteristicas del equipo
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
@Table(name = "ficha_tecnica")
public class FichaTecnica {

    /** Identificador unico de la ficha tecnica */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ficha")
    private Long idFicha;


    // Relacion con la entidad equipo
    @OneToOne
    @JoinColumn(name = "id_equipo", unique = true, nullable = false)
    private Equipo equipo;


    /** Marca del equipo */
    @NotBlank
    private String marca;


    /** Modelo del equipo */
    @NotBlank
    private String modelo;


    /** Fecha de compra del equipo */
    @Column(name = "fecha_de_compra")
    private LocalDate fechaCompra;


    /** Fecha de puesta en marcha del equipo */
    @Column(name = "fecha_de_puesta_en_marcha")
    private LocalDate fechaPuestaEnMarcha;


    /** Costo de adquisicion del equipo */
    @NotNull
    @Column(name = "costo_de_adquisicion", precision = 11, scale = 2)
    private BigDecimal costoAdquisicion;


    /** Caracteristicas fisicas del equipo */
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String caracteristicasFisicas;


    /** Caracteristicas electronicas del equipo */
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String caracteristicasElectronicas;


    /** Otros detalles del equipo */
    @Column(columnDefinition = "TEXT")
    private String otrosDetalles;

    /** Indica si la ficha técnica está activa (soft-delete) */
    @Column(name = "activo")
    private Boolean activo;


}
