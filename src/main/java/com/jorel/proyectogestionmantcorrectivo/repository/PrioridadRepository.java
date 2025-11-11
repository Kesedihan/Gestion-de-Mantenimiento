package com.jorel.proyectogestionmantcorrectivo.repository;

import com.jorel.proyectogestionmantcorrectivo.entity.NivelPrioridad;
import com.jorel.proyectogestionmantcorrectivo.entity.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para consultar los niveles de prioridad disponibles en el sistema.
 *
 * @author Marlon
 * @version 1.0
 *
 */

@Repository
public interface PrioridadRepository  extends JpaRepository<Prioridad,Long> {
    /**
     * Busca una prioridad por su nivel (BAJA, MEDIA, ALTA, CRÍTICA).
     */
    Optional<Prioridad> findByNivel(NivelPrioridad nivel);
}
