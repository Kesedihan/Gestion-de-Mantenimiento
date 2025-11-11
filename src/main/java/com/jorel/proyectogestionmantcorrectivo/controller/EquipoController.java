package com.jorel.proyectogestionmantcorrectivo.controller;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.EquipoDTO;
import com.jorel.proyectogestionmantcorrectivo.service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EquipoController {

    private final EquipoService equipoService;

    @GetMapping
    public ResponseEntity<List<EquipoDTO>> listar() {
        return ResponseEntity.ok(equipoService.listarEquipos());
    }

    @PostMapping
    public ResponseEntity<EquipoDTO> crear(@RequestBody EquipoDTO dto) {
        return ResponseEntity.ok(equipoService.guardarEquipo(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> obtener(@PathVariable Long id) {
        return equipoService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> actualizar(@PathVariable Long id, @RequestBody EquipoDTO dto) {
        return ResponseEntity.ok(equipoService.actualizarEquipo(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        equipoService.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
