package com.jorel.proyectogestionmantcorrectivo.service;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.RolDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {

    RolDTO crear(RolDTO rolDTO);

    RolDTO actualizar(Long id, RolDTO rolDTO);

    List<RolDTO> listarTodos();

    Optional<RolDTO> buscarPorId(Long id);

    void eliminar(Long id);

}
