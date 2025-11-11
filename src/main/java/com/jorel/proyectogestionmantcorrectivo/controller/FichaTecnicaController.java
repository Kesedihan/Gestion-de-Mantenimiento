package com.jorel.proyectogestionmantcorrectivo.controller;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.FichaTecnicaDTO;
import com.jorel.proyectogestionmantcorrectivo.service.FichaTecnicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fichas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FichaTecnicaController {

    private final FichaTecnicaService fichaService;

    @GetMapping
    public ResponseEntity<List<FichaTecnicaDTO>> listar() {
        return ResponseEntity.ok(fichaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FichaTecnicaDTO> obtener(@PathVariable Long id) {
        return fichaService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<FichaTecnicaDTO> obtenerPorEquipo(@PathVariable Long equipoId) {
        return fichaService.buscarPorEquipoId(equipoId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FichaTecnicaDTO> crear(@RequestBody FichaTecnicaDTO dto) {
        return ResponseEntity.ok(fichaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FichaTecnicaDTO> actualizar(@PathVariable Long id, @RequestBody FichaTecnicaDTO dto) {
        return ResponseEntity.ok(fichaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        fichaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
