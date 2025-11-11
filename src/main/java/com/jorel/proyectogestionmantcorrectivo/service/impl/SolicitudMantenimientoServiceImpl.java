package com.jorel.proyectogestionmantcorrectivo.service.impl;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.SolicitudMantenimientoDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Equipo;
import com.jorel.proyectogestionmantcorrectivo.entity.SolicitudMantenimiento;
import com.jorel.proyectogestionmantcorrectivo.entity.Usuario;
import com.jorel.proyectogestionmantcorrectivo.repository.EquipoRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.SolicitudMantenimientoRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.UsuarioRepository;
import com.jorel.proyectogestionmantcorrectivo.service.SolicitudMantenimientoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SolicitudMantenimientoServiceImpl implements SolicitudMantenimientoService {

    private final SolicitudMantenimientoRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipoRepository equipoRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<SolicitudMantenimientoDTO> listarTodas() {
        return solicitudRepository.findAll()
                .stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudMantenimientoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SolicitudMantenimientoDTO> obtenerPorId(Long id) {
        return solicitudRepository.findById(id)
                .map(solicitud -> modelMapper.map(solicitud, SolicitudMantenimientoDTO.class));
    }

    @Override
    public SolicitudMantenimientoDTO crear(SolicitudMantenimientoDTO solicitudDTO) {
        SolicitudMantenimiento solicitud = modelMapper.map(solicitudDTO, SolicitudMantenimiento.class);

        // Asociar Usuario
        Usuario usuario = usuarioRepository.findById(solicitudDTO.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + solicitudDTO.getIdUsuario()));
        solicitud.setUsuario(usuario);

        // Asociar Equipo
        Equipo equipo = equipoRepository.findById(solicitudDTO.getIdEquipo())
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + solicitudDTO.getIdEquipo()));
        solicitud.setEquipo(equipo);

        // Guardar en la BD
        SolicitudMantenimiento guardada = solicitudRepository.save(solicitud);
        return modelMapper.map(guardada, SolicitudMantenimientoDTO.class);
    }

    @Override
    public SolicitudMantenimientoDTO actualizar(Long id, SolicitudMantenimientoDTO solicitudDTO) {
        SolicitudMantenimiento solicitudExistente = solicitudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada con ID: " + id));

        solicitudExistente.setTipoFalla(solicitudDTO.getTipoFalla());
        solicitudExistente.setDescripcionFalla(solicitudDTO.getDescripcionFalla());
        solicitudExistente.setFechaSolicitud(solicitudDTO.getFechaSolicitud());
        solicitudExistente.setEstadoSolicitud(solicitudDTO.getEstadoSolicitud());

        // Actualizar relaciones si cambian
        if (solicitudDTO.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(solicitudDTO.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + solicitudDTO.getIdUsuario()));
            solicitudExistente.setUsuario(usuario);
        }

        if (solicitudDTO.getIdEquipo() != null) {
            Equipo equipo = equipoRepository.findById(solicitudDTO.getIdEquipo())
                    .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + solicitudDTO.getIdEquipo()));
            solicitudExistente.setEquipo(equipo);
        }

        SolicitudMantenimiento actualizada = solicitudRepository.save(solicitudExistente);
        return modelMapper.map(actualizada, SolicitudMantenimientoDTO.class);
    }

    @Override
    public void eliminar(Long id) {
        if (!solicitudRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe una solicitud con el ID: " + id);
        }
        solicitudRepository.deleteById(id);
    }


}
