package com.jorel.proyectogestionmantcorrectivo.service;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.FichaTecnicaDTO;

import java.util.List;
import java.util.Optional;

public interface FichaTecnicaService {
    FichaTecnicaDTO crear(FichaTecnicaDTO dto);
    FichaTecnicaDTO actualizar(Long id, FichaTecnicaDTO dto);
    Optional<FichaTecnicaDTO> buscarPorId(Long id);
    Optional<FichaTecnicaDTO> buscarPorEquipoId(Long equipoId);
    List<FichaTecnicaDTO> listarTodas();
    void eliminar(Long id);
}
