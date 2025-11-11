package com.jorel.proyectogestionmantcorrectivo.service;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.EquipoDTO;

import java.util.List;
import java.util.Optional;

public interface EquipoService {
    List<EquipoDTO> listarEquipos();
    Optional<EquipoDTO> obtenerPorId(Long id);
    EquipoDTO guardarEquipo(EquipoDTO dto);
    EquipoDTO actualizarEquipo(Long id, EquipoDTO dto);
    void eliminarEquipo(Long id);
}
