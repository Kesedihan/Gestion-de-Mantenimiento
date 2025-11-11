package com.jorel.proyectogestionmantcorrectivo.service;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.SolicitudMantenimientoDTO;

import java.util.List;
import java.util.Optional;

public interface SolicitudMantenimientoService {

    List<SolicitudMantenimientoDTO> listarTodas();

    Optional<SolicitudMantenimientoDTO> obtenerPorId(Long id);

    SolicitudMantenimientoDTO crear(SolicitudMantenimientoDTO solicitudDTO);

    SolicitudMantenimientoDTO actualizar(Long id, SolicitudMantenimientoDTO solicitudDTO);

    void eliminar(Long id);

}
