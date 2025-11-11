package com.jorel.proyectogestionmantcorrectivo.controller;


import com.jorel.proyectogestionmantcorrectivo.controller.dto.UsuarioDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Usuario;
import com.jorel.proyectogestionmantcorrectivo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.security.Principal;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite pruebas desde Postman o frontends externos
public class UsuarioController {

    /**
     * Controlador REST para operaciones CRUD sobre la entidad Usuario.
     * Nota: el endpoint POST /api/usuarios se dejó público para permitir el flujo de registro
     * desde el frontend (POST sin token). Las demás rutas requieren autenticación por JWT.
     */

    private final UsuarioService usuarioService;

    // Listar los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }


    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuarioDTO));
    }


    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuarioDTO));
    }


    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }


    // Asignar rol a usuario
    @PutMapping("/{idUsuario}/rol/{idRol}")
    public ResponseEntity<UsuarioDTO> asignarRol(@PathVariable Long idUsuario, @PathVariable Long idRol) {
        return ResponseEntity.ok(usuarioService.asignarRol(idUsuario, idRol));
    }

    // Obtener el usuario actualmente autenticado
    @GetMapping("/me")
    public ResponseEntity<Usuario> me(Principal principal) {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(401).build();
        }
        return usuarioService.buscarPorCorreo(principal.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
