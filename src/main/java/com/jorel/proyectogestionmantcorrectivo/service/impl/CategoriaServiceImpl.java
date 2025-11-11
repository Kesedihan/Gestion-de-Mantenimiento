package com.jorel.proyectogestionmantcorrectivo.service.impl;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.CategoriaDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Categoria;
import com.jorel.proyectogestionmantcorrectivo.repository.CategoriaRepository;
import com.jorel.proyectogestionmantcorrectivo.service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoriaDTO crear(CategoriaDTO dto) {
        Categoria c = modelMapper.map(dto, Categoria.class);
        if (c.getActivo() == null) c.setActivo(true);
        Categoria saved = categoriaRepository.save(c);
        return modelMapper.map(saved, CategoriaDTO.class);
    }

    @Override
    public CategoriaDTO actualizar(Long id, CategoriaDTO dto) {
        Categoria existing = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada: " + id));
        existing.setNombre(dto.getNombre());
        existing.setDescripcion(dto.getDescripcion());
        Categoria updated = categoriaRepository.save(existing);
        return modelMapper.map(updated, CategoriaDTO.class);
    }

    @Override
    public List<CategoriaDTO> listarTodas() {
    return categoriaRepository.findAll().stream()
        .filter(c -> c.getActivo() == null ? true : c.getActivo())
        .map(c -> modelMapper.map(c, CategoriaDTO.class))
        .toList();
    }

    @Override
    public Optional<CategoriaDTO> buscarPorId(Long id) {
        return categoriaRepository.findById(id).map(c -> modelMapper.map(c, CategoriaDTO.class));
    }

    @Override
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria no encontrada: " + id);
        }
        Categoria existing = categoriaRepository.findById(id).orElseThrow();
        if (existing.getActivo() != null && !existing.getActivo()) return;
        existing.setActivo(false);
        categoriaRepository.save(existing);
    }
}
