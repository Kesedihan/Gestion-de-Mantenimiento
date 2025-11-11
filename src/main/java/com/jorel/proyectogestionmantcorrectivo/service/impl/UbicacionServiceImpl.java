package com.jorel.proyectogestionmantcorrectivo.service.impl;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.UbicacionDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Ubicacion;
import com.jorel.proyectogestionmantcorrectivo.repository.UbicacionRepository;
import com.jorel.proyectogestionmantcorrectivo.service.UbicacionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final ModelMapper modelMapper;

    @Override
    public UbicacionDTO crear(UbicacionDTO dto) {
        Ubicacion u = modelMapper.map(dto, Ubicacion.class);
        if (u.getActivo() == null) u.setActivo(true);
        Ubicacion saved = ubicacionRepository.save(u);
        return modelMapper.map(saved, UbicacionDTO.class);
    }

    @Override
    public UbicacionDTO actualizar(Long id, UbicacionDTO dto) {
        Ubicacion existing = ubicacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ubicacion no encontrada: " + id));
        existing.setNombre(dto.getNombre());
        existing.setDescripcion(dto.getDescripcion());
        Ubicacion updated = ubicacionRepository.save(existing);
        return modelMapper.map(updated, UbicacionDTO.class);
    }

    @Override
    public List<UbicacionDTO> listarTodas() {
    return ubicacionRepository.findAll().stream()
        .filter(u -> u.getActivo() == null ? true : u.getActivo())
        .map(u -> modelMapper.map(u, UbicacionDTO.class))
        .toList();
    }

    @Override
    public Optional<UbicacionDTO> buscarPorId(Long id) {
        return ubicacionRepository.findById(id).map(u -> modelMapper.map(u, UbicacionDTO.class));
    }

    @Override
    public void eliminar(Long id) {
        if (!ubicacionRepository.existsById(id)) {
            throw new EntityNotFoundException("Ubicacion no encontrada: " + id);
        }
        Ubicacion existing = ubicacionRepository.findById(id).orElseThrow();
        if (existing.getActivo() != null && !existing.getActivo()) return;
        existing.setActivo(false);
        ubicacionRepository.save(existing);
    }
}
