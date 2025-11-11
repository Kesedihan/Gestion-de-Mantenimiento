package com.jorel.proyectogestionmantcorrectivo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FichaTecnicaDTO {
    private Long idFicha;
    private Long equipoId;
    private String marca;
    private String modelo;
    private LocalDate fechaCompra;
    private LocalDate fechaPuestaEnMarcha;
    private BigDecimal costoAdquisicion;
    private String caracteristicasFisicas;
    private String caracteristicasElectronicas;
    private String otrosDetalles;
    private Boolean activo;
}
