package com.jorel.proyectogestionmantcorrectivo.repository;

import com.jorel.proyectogestionmantcorrectivo.entity.EstadoOrdenTrabajo;
import com.jorel.proyectogestionmantcorrectivo.entity.OrdenDeTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para gestionar las órdenes de trabajo.
 */

public interface OrdenDeTrabajoRepository extends JpaRepository<OrdenDeTrabajo,Long> {
    /**
     * Busca órdenes por estado (ASIGNADA, EN_PROCESO, FINALIZADA).
     */
    List<OrdenDeTrabajo> findByEstado(EstadoOrdenTrabajo estado);
}
