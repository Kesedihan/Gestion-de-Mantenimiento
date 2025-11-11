package com.jorel.proyectogestionmantcorrectivo.service;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.UsuarioDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<UsuarioDTO> listarUsuarios();

    Optional<UsuarioDTO> obtenerPorId(Long id);

    UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO);

    void eliminarUsuario(Long id);

    UsuarioDTO asignarRol(Long idUsuario, Long idRol);

    java.util.Optional<com.jorel.proyectogestionmantcorrectivo.entity.Usuario> buscarPorCorreo(String correo);

}
