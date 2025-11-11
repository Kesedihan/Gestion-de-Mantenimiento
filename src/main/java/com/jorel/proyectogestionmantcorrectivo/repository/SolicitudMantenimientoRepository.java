package com.jorel.proyectogestionmantcorrectivo.repository;

import com.jorel.proyectogestionmantcorrectivo.entity.EstadoSolicitud;
import com.jorel.proyectogestionmantcorrectivo.entity.SolicitudMantenimiento;
import com.jorel.proyectogestionmantcorrectivo.entity.TipoFalla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudMantenimientoRepository extends JpaRepository<SolicitudMantenimiento, Long> {


    /**
     * Busca solicitudes por su estado (PENDIENTE, EN_PROCESO, CERRADA).
     */
    List<SolicitudMantenimiento> findByEstadoSolicitud(EstadoSolicitud estado);

    /**
     * Busca solicitudes por tipo de falla (ELECTRICA, MECANICA, etc.).
     */
    List<SolicitudMantenimiento> findByTipoFalla(TipoFalla tipoFalla);
}
