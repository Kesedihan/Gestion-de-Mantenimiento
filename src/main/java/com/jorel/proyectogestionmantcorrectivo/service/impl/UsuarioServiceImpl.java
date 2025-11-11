package com.jorel.proyectogestionmantcorrectivo.service.impl;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.UsuarioDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Rol;
import com.jorel.proyectogestionmantcorrectivo.entity.Usuario;
import com.jorel.proyectogestionmantcorrectivo.repository.RolRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.UsuarioRepository;
import com.jorel.proyectogestionmantcorrectivo.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .toList();
    }


    @Override
    public Optional<UsuarioDTO> obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }


    @Override
    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

        // IMPORTANTE: se asume que la contraseña que llega en el DTO está en texto plano.
        // Antes de persistir, la ciframos con BCrypt para que nunca se almacene en claro.
        if (usuario.getContrasena() != null) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }

        Usuario guardado = usuarioRepository.save(usuario);
        return modelMapper.map(guardado, UsuarioDTO.class);
    }


    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setCorreo(usuarioDTO.getCorreo());
        usuarioExistente.setCelular(usuarioDTO.getCelular());
        usuarioExistente.setActivo(usuarioDTO.getActivo());
        usuarioExistente.setDescripcionCargo(usuarioDTO.getDescripcionCargo());

        if (usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().isBlank()) {
            usuarioExistente.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        }

        Usuario actualizado = usuarioRepository.save(usuarioExistente);
        return modelMapper.map(actualizado, UsuarioDTO.class);
    }


    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe un usuario con el ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }


    @Override
    public UsuarioDTO asignarRol(Long idUsuario, Long idRol) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

        Rol rol = rolRepository.findById(Math.toIntExact(idRol))
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + idRol));

        usuario.getRoles().add(rol);
        Usuario actualizado = usuarioRepository.save(usuario);

        return modelMapper.map(actualizado, UsuarioDTO.class);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }



}
