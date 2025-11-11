package com.jorel.proyectogestionmantcorrectivo.controller;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.OrdenDeTrabajoDTO;
import com.jorel.proyectogestionmantcorrectivo.service.OrdenDeTrabajoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite pruebas desde Postman o frontends externos
public class OrdenDeTrabajoController {

    private final OrdenDeTrabajoService ordenService;

    @GetMapping
    public ResponseEntity<List<OrdenDeTrabajoDTO>> listarTodas() {
        return ResponseEntity.ok(ordenService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenDeTrabajoDTO> obtenerPorId(@PathVariable Long id) {
        return ordenService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrdenDeTrabajoDTO> crear(@Valid @RequestBody OrdenDeTrabajoDTO ordenDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordenService.crear(ordenDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenDeTrabajoDTO> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody OrdenDeTrabajoDTO ordenDTO) {
        return ResponseEntity.ok(ordenService.actualizar(id, ordenDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ordenService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
