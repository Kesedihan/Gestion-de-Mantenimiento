package com.jorel.proyectogestionmantcorrectivo.service.impl;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.FichaTecnicaDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Equipo;
import com.jorel.proyectogestionmantcorrectivo.entity.FichaTecnica;
import com.jorel.proyectogestionmantcorrectivo.repository.EquipoRepository;
import com.jorel.proyectogestionmantcorrectivo.repository.FichaTecnicaRepository;
import com.jorel.proyectogestionmantcorrectivo.service.FichaTecnicaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FichaTecnicaServiceImpl implements FichaTecnicaService {

    private final FichaTecnicaRepository fichaRepo;
    private final EquipoRepository equipoRepository;
    private final ModelMapper modelMapper;

    @Override
    public FichaTecnicaDTO crear(FichaTecnicaDTO dto) {
        Equipo equipo = equipoRepository.findById(dto.getEquipoId())
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado: " + dto.getEquipoId()));

        FichaTecnica f = modelMapper.map(dto, FichaTecnica.class);
        f.setEquipo(equipo);
        if (f.getActivo() == null) f.setActivo(true);
        FichaTecnica saved = fichaRepo.save(f);
        return modelMapper.map(saved, FichaTecnicaDTO.class);
    }

    @Override
    public FichaTecnicaDTO actualizar(Long id, FichaTecnicaDTO dto) {
        FichaTecnica existing = fichaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ficha no encontrada: " + id));

        existing.setMarca(dto.getMarca());
        existing.setModelo(dto.getModelo());
        existing.setFechaCompra(dto.getFechaCompra());
        existing.setFechaPuestaEnMarcha(dto.getFechaPuestaEnMarcha());
        existing.setCostoAdquisicion(dto.getCostoAdquisicion());
        existing.setCaracteristicasFisicas(dto.getCaracteristicasFisicas());
        existing.setCaracteristicasElectronicas(dto.getCaracteristicasElectronicas());
        existing.setOtrosDetalles(dto.getOtrosDetalles());

        FichaTecnica updated = fichaRepo.save(existing);
        return modelMapper.map(updated, FichaTecnicaDTO.class);
    }

    @Override
    public Optional<FichaTecnicaDTO> buscarPorId(Long id) {
        return fichaRepo.findById(id).map(f -> modelMapper.map(f, FichaTecnicaDTO.class));
    }

    @Override
    public Optional<FichaTecnicaDTO> buscarPorEquipoId(Long equipoId) {
        return fichaRepo.findByEquipo_IdEquipo(equipoId).map(f -> modelMapper.map(f, FichaTecnicaDTO.class));
    }

    @Override
    public List<FichaTecnicaDTO> listarTodas() {
        return fichaRepo.findAll().stream()
                .filter(f -> f.getActivo() == null ? true : f.getActivo())
                .map(f -> modelMapper.map(f, FichaTecnicaDTO.class))
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        if (!fichaRepo.existsById(id)) {
            throw new EntityNotFoundException("Ficha no encontrada: " + id);
        }
        FichaTecnica existing = fichaRepo.findById(id).orElseThrow();
        if (existing.getActivo() != null && !existing.getActivo()) return;
        existing.setActivo(false);
        fichaRepo.save(existing);
    }
}
