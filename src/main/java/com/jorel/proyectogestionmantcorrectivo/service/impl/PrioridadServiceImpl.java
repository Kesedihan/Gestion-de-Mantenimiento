package com.jorel.proyectogestionmantcorrectivo.service.impl;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.PrioridadDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Prioridad;
import com.jorel.proyectogestionmantcorrectivo.repository.PrioridadRepository;
import com.jorel.proyectogestionmantcorrectivo.service.PrioridadService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PrioridadServiceImpl implements PrioridadService {

    private final PrioridadRepository prioridadRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PrioridadDTO> listarTodas() {
        return prioridadRepository.findAll()
                .stream()
                .map(prioridad -> modelMapper.map(prioridad, PrioridadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PrioridadDTO> obtenerPorId(Long id) {
        return prioridadRepository.findById(id)
                .map(prioridad -> modelMapper.map(prioridad, PrioridadDTO.class));
    }

    @Override
    public PrioridadDTO crear(PrioridadDTO prioridadDTO) {
        Prioridad prioridad = modelMapper.map(prioridadDTO, Prioridad.class);
        return modelMapper.map(prioridadRepository.save(prioridad), PrioridadDTO.class);
    }

    @Override
    public PrioridadDTO actualizar(Long id, PrioridadDTO prioridadDTO) {
        Prioridad prioridadExistente = prioridadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe una prioridad con ID: " + id));

        prioridadExistente.setNivel(prioridadDTO.getNivel());
        prioridadExistente.setTiempoMaxRespuesta(prioridadDTO.getTiempoMaxRespuesta());

        Prioridad prioridadActualizada = prioridadRepository.save(prioridadExistente);
        return modelMapper.map(prioridadActualizada, PrioridadDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        if (!prioridadRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe una prioridad con el ID: " + id);
        }
        prioridadRepository.deleteById(id);
    }

}
