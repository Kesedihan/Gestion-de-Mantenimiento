package com.jorel.proyectogestionmantcorrectivo.service;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.UbicacionDTO;

import java.util.List;
import java.util.Optional;

public interface UbicacionService {
    UbicacionDTO crear(UbicacionDTO dto);
    UbicacionDTO actualizar(Long id, UbicacionDTO dto);
    List<UbicacionDTO> listarTodas();
    Optional<UbicacionDTO> buscarPorId(Long id);
    void eliminar(Long id);
}
