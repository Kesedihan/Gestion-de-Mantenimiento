package com.jorel.proyectogestionmantcorrectivo.controller;


import com.jorel.proyectogestionmantcorrectivo.controller.dto.SolicitudMantenimientoDTO;
import com.jorel.proyectogestionmantcorrectivo.service.SolicitudMantenimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite pruebas desde Postman o frontends externos
public class SolicitudMantenimientoController {

    private final SolicitudMantenimientoService solicitudService;

    @GetMapping
    public ResponseEntity<List<SolicitudMantenimientoDTO>> listarTodas() {
        return ResponseEntity.ok(solicitudService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudMantenimientoDTO> obtenerPorId(@PathVariable Long id) {
        return solicitudService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SolicitudMantenimientoDTO> crear(
            @Valid @RequestBody SolicitudMantenimientoDTO solicitudDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(solicitudService.crear(solicitudDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudMantenimientoDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudMantenimientoDTO solicitudDTO) {
        return ResponseEntity.ok(solicitudService.actualizar(id, solicitudDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        solicitudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
