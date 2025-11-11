package com.jorel.proyectogestionmantcorrectivo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hoja_de_vida")
public class HojaDeVida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegistro;

    @OneToOne
    @JoinColumn(name = "id_equipo")
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "id_orden")
    private OrdenDeTrabajo orden;

    private String fechaMantenimiento;
    private String descripcionFalla;
    private String accionRealizada;
    private String observaciones;
}
