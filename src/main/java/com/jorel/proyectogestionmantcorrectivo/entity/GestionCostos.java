package com.jorel.proyectogestionmantcorrectivo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "gestion_costos")


public class GestionCostos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCosto;

    @OneToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenDeTrabajo orden;

    @Column(name = "costo_repuestos", precision = 11, scale = 2)
    private BigDecimal costoRepuestos;

    @Column(name = "otros_gastos", precision = 11, scale = 2)
    private BigDecimal otrosGastos;

    @Column(name = "costo_total", precision = 11, scale = 2, insertable = false, updatable = false)
    private BigDecimal costoTotal;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

}
