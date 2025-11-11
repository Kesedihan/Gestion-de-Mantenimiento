package com.jorel.proyectogestionmantcorrectivo.service;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.CategoriaDTO;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    CategoriaDTO crear(CategoriaDTO dto);
    CategoriaDTO actualizar(Long id, CategoriaDTO dto);
    List<CategoriaDTO> listarTodas();
    Optional<CategoriaDTO> buscarPorId(Long id);
    void eliminar(Long id);
}
