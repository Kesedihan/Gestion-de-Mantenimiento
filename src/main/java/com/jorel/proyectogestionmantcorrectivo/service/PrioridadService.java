package com.jorel.proyectogestionmantcorrectivo.service;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.PrioridadDTO;

import java.util.List;
import java.util.Optional;

public interface PrioridadService {

    List<PrioridadDTO> listarTodas();

    Optional<PrioridadDTO> obtenerPorId(Long id);

    PrioridadDTO crear(PrioridadDTO prioridadDTO);

    PrioridadDTO actualizar(Long id, PrioridadDTO prioridadDTO);

    void eliminar(Long id);

}
