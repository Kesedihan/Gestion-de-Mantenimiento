package com.jorel.proyectogestionmantcorrectivo.controller;


import com.jorel.proyectogestionmantcorrectivo.controller.dto.RolDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Rol;
import com.jorel.proyectogestionmantcorrectivo.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite pruebas desde Postman o frontends externos
public class RolController {

    private final RolService rolService;

    // Listar roles
    @GetMapping
    public ResponseEntity<List<RolDTO>> listarRoles() {
        return ResponseEntity.ok(rolService.listarTodos());
    }


    // Crear rol
    @PostMapping
    public ResponseEntity<RolDTO> crearRol(@RequestBody RolDTO rolDTO) {
        return ResponseEntity.ok(rolService.crear(rolDTO));
    }


    // Obtener rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> obtenerPorId(@PathVariable Long id) {
        return rolService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Actualizar rol
    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> actualizarRol(@PathVariable Long id, @RequestBody RolDTO rolDTO) {
        return ResponseEntity.ok(rolService.actualizar(id, rolDTO));
    }


    // Eliminar rol
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
