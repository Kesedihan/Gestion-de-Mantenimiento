package com.jorel.proyectogestionmantcorrectivo.controller;


import com.jorel.proyectogestionmantcorrectivo.controller.dto.PrioridadDTO;
import com.jorel.proyectogestionmantcorrectivo.service.PrioridadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prioridades")
@RequiredArgsConstructor
public class PrioridadController {

    private final PrioridadService prioridadService;

    @GetMapping
    public ResponseEntity<List<PrioridadDTO>> listarTodas() {
        return ResponseEntity.ok(prioridadService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrioridadDTO> obtenerPorId(@PathVariable Long id) {
        return prioridadService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PrioridadDTO> crear(@Valid @RequestBody PrioridadDTO prioridadDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prioridadService.crear(prioridadDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrioridadDTO> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody PrioridadDTO prioridadDTO) {
        return ResponseEntity.ok(prioridadService.actualizar(id, prioridadDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        prioridadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
