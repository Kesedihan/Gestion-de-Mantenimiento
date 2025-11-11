package com.jorel.proyectogestionmantcorrectivo.service.impl;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.OrdenDeTrabajoDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.OrdenDeTrabajo;
import com.jorel.proyectogestionmantcorrectivo.entity.Prioridad;
import com.jorel.proyectogestionmantcorrectivo.entity.SolicitudMantenimiento;
import com.jorel.proyectogestionmantcorrectivo.entity.Usuario;
import com.jorel.proyectogestionmantcorrectivo.repository.OrdenDeTrabajoRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.PrioridadRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.SolicitudMantenimientoRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.UsuarioRepository;
import com.jorel.proyectogestionmantcorrectivo.service.OrdenDeTrabajoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrdenDeTrabajoServiceImpl implements OrdenDeTrabajoService {

    private final OrdenDeTrabajoRepository ordenDeTrabajoRepository;
    private final SolicitudMantenimientoRepository solicitudMantenimientoRepository;
    private final PrioridadRepository prioridadRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrdenDeTrabajoDTO> listarTodos() {
        return ordenDeTrabajoRepository.findAll()
                .stream()
                .map(orden -> modelMapper.map(orden, OrdenDeTrabajoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrdenDeTrabajoDTO> obtenerPorId(Long id) {
        return ordenDeTrabajoRepository.findById(id)
                .map(orden -> modelMapper.map(orden, OrdenDeTrabajoDTO.class));
    }

    @Override
    public OrdenDeTrabajoDTO crear(OrdenDeTrabajoDTO ordenDTO) {
        OrdenDeTrabajo orden = modelMapper.map(ordenDTO, OrdenDeTrabajo.class);

        // Validar entidades relacionadas
        SolicitudMantenimiento solicitud = solicitudMantenimientoRepository.findById(
                ordenDTO.getSolicitudId()).orElseThrow(() ->
                new EntityNotFoundException("Solicitud no encontrada con ID: " + ordenDTO.getSolicitudId()));

        Prioridad prioridad = prioridadRepository.findById(
                ordenDTO.getPrioridadId()).orElseThrow(() ->
                new EntityNotFoundException("Prioridad no encontrada con ID: " + ordenDTO.getPrioridadId()));

        Usuario tecnico = usuarioRepository.findById(
                ordenDTO.getTecnicoId()).orElseThrow(() ->
                new EntityNotFoundException("Técnico no encontrado con ID: " + ordenDTO.getTecnicoId()));

        // Asignar relaciones
        orden.setSolicitud(solicitud);
        orden.setPrioridad(prioridad);
        orden.setTecnico(tecnico);

        OrdenDeTrabajo guardada = ordenDeTrabajoRepository.save(orden);
        return modelMapper.map(guardada, OrdenDeTrabajoDTO.class);
    }

    @Override
    public OrdenDeTrabajoDTO actualizar(Long id, OrdenDeTrabajoDTO ordenDTO) {
        OrdenDeTrabajo existente = ordenDeTrabajoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden de trabajo no encontrada con ID: " + id));

        // Actualizar campos
        existente.setDiagnostico(ordenDTO.getDiagnostico());
        existente.setSolucion(ordenDTO.getSolucion());
        existente.setEstado(ordenDTO.getEstado());
        existente.setFechaCierre(ordenDTO.getFechaCierre());
        existente.setFechaAsignacion(ordenDTO.getFechaAsignacion());

        // Actualizar relaciones si se envían nuevas
        if (ordenDTO.getPrioridadId() != null) {
            Prioridad prioridad = prioridadRepository.findById(ordenDTO.getPrioridadId())
                    .orElseThrow(() -> new EntityNotFoundException("Prioridad no encontrada con ID: " + ordenDTO.getPrioridadId()));
            existente.setPrioridad(prioridad);
        }

        if (ordenDTO.getTecnicoId() != null) {
            Usuario tecnico = usuarioRepository.findById(ordenDTO.getTecnicoId())
                    .orElseThrow(() -> new EntityNotFoundException("Técnico no encontrado con ID: " + ordenDTO.getTecnicoId()));
            existente.setTecnico(tecnico);
        }

        OrdenDeTrabajo actualizada = ordenDeTrabajoRepository.save(existente);
        return modelMapper.map(actualizada, OrdenDeTrabajoDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        if (!ordenDeTrabajoRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe una orden de trabajo con ID: " + id);
        }
        ordenDeTrabajoRepository.deleteById(id);
    }


}
