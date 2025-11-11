package com.jorel.proyectogestionmantcorrectivo.service.impl;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.EquipoDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Equipo;
import com.jorel.proyectogestionmantcorrectivo.entity.Categoria;
import com.jorel.proyectogestionmantcorrectivo.entity.Ubicacion;
import com.jorel.proyectogestionmantcorrectivo.repository.EquipoRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.CategoriaRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.UbicacionRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.FichaTecnicaRepository;
import com.jorel.proyectogestionmantcorrectivo.service.EquipoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UbicacionRepository ubicacionRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<EquipoDTO> listarEquipos() {
        return equipoRepository.findAll().stream()
                .map(e -> {
                    EquipoDTO dto = modelMapper.map(e, EquipoDTO.class);
                    if (e.getCategoria() != null) {
                        dto.setCategoriaId(e.getCategoria().getIdCategoria());
                        dto.setCategoriaNombre(e.getCategoria().getNombre());
                    }
                    if (e.getUbicacion() != null) {
                        dto.setUbicacionId(e.getUbicacion().getIdUbicacion());
                        dto.setUbicacionNombre(e.getUbicacion().getNombre());
                    }
                    // Añadir resumen de ficha técnica si existe
                    fichaTecnicaRepository.findByEquipo_IdEquipo(e.getIdEquipo()).ifPresent(f -> {
                        dto.setFichaId(f.getIdFicha());
                        dto.setFichaMarca(f.getMarca());
                        dto.setFichaModelo(f.getModelo());
                    });
                    return dto;
                }).toList();
    }

    @Override
    public Optional<EquipoDTO> obtenerPorId(Long id) {
        return equipoRepository.findById(id).map(e -> {
            EquipoDTO dto = modelMapper.map(e, EquipoDTO.class);
            if (e.getCategoria() != null) {
                dto.setCategoriaId(e.getCategoria().getIdCategoria());
                dto.setCategoriaNombre(e.getCategoria().getNombre());
            }
            if (e.getUbicacion() != null) {
                dto.setUbicacionId(e.getUbicacion().getIdUbicacion());
                dto.setUbicacionNombre(e.getUbicacion().getNombre());
            }
            fichaTecnicaRepository.findByEquipo_IdEquipo(e.getIdEquipo()).ifPresent(f -> {
                dto.setFichaId(f.getIdFicha());
                dto.setFichaMarca(f.getMarca());
                dto.setFichaModelo(f.getModelo());
            });
            return dto;
        });
    }

    @Override
    public EquipoDTO guardarEquipo(EquipoDTO dto) {
        Equipo e = new Equipo();
        e.setNombre(dto.getNombre());
        e.setActivo(dto.getActivo() == null ? true : dto.getActivo());
        e.setCategoria(null);
        e.setUbicacion(null);
        e.setDescripcion(dto.getDescripcion());

        if (dto.getCategoriaId() != null) {
            Categoria c = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada: " + dto.getCategoriaId()));
            e.setCategoria(c);
        }

        if (dto.getUbicacionId() != null) {
            Ubicacion u = ubicacionRepository.findById(dto.getUbicacionId())
                    .orElseThrow(() -> new EntityNotFoundException("Ubicacion no encontrada: " + dto.getUbicacionId()));
            e.setUbicacion(u);
        }

        Equipo saved = equipoRepository.save(e);
        return obtenerPorId(saved.getIdEquipo()).orElseThrow();
    }

    @Override
    public EquipoDTO actualizarEquipo(Long id, EquipoDTO dto) {
        Equipo existing = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado: " + id));

        existing.setNombre(dto.getNombre());
        existing.setActivo(dto.getActivo() == null ? existing.getActivo() : dto.getActivo());
        existing.setDescripcion(dto.getDescripcion());

        if (dto.getCategoriaId() != null) {
            Categoria c = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada: " + dto.getCategoriaId()));
            existing.setCategoria(c);
        }

        if (dto.getUbicacionId() != null) {
            Ubicacion u = ubicacionRepository.findById(dto.getUbicacionId())
                    .orElseThrow(() -> new EntityNotFoundException("Ubicacion no encontrada: " + dto.getUbicacionId()));
            existing.setUbicacion(u);
        }

        Equipo updated = equipoRepository.save(existing);
        return obtenerPorId(updated.getIdEquipo()).orElseThrow();
    }

    @Override
    public void eliminarEquipo(Long id) {
        // Soft-delete: marcar como inactivo en vez de borrar para preservar fichas técnicas relacionadas
        Equipo existing = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado: " + id));

        // Si ya está inactivo, no hacemos nada (idempotente)
        if (existing.getActivo() != null && !existing.getActivo()) {
            return;
        }

        existing.setActivo(false);
        equipoRepository.save(existing);
    }
}
