package com.jorel.proyectogestionmantcorrectivo.service;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.OrdenDeTrabajoDTO;

import java.util.List;
import java.util.Optional;

public interface OrdenDeTrabajoService {

    List<OrdenDeTrabajoDTO> listarTodos();

    Optional<OrdenDeTrabajoDTO> obtenerPorId(Long id);

    OrdenDeTrabajoDTO crear(OrdenDeTrabajoDTO ordenDTO);

    OrdenDeTrabajoDTO actualizar(Long id, OrdenDeTrabajoDTO ordenDTO);

    void eliminar(Long id);

}
