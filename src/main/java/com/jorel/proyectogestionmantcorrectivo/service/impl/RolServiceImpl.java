package com.jorel.proyectogestionmantcorrectivo.service.impl;

import com.jorel.proyectogestionmantcorrectivo.controller.dto.RolDTO;
import com.jorel.proyectogestionmantcorrectivo.entity.Rol;
import com.jorel.proyectogestionmantcorrectivo.repository.RolRepository;
import com.jorel.proyectogestionmantcorrectivo.service.RolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final ModelMapper modelMapper;


    @Override
    public RolDTO crear(RolDTO rolDTO) {
        Rol rol = modelMapper.map(rolDTO, Rol.class);
        Rol guardado = rolRepository.save(rol);
        return modelMapper.map(guardado, RolDTO.class);
    }


    @Override
    public RolDTO actualizar(Long id, RolDTO rolDTO) {
        Rol rolExistente = rolRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));

        rolExistente.setNombre(rolDTO.getNombre());
        Rol actualizado = rolRepository.save(rolExistente);

        return modelMapper.map(actualizado, RolDTO.class);
    }


    @Override
    public List<RolDTO> listarTodos() {
        return rolRepository.findAll()
                .stream()
                .map(rol -> modelMapper.map(rol, RolDTO.class))
                .toList();
    }


    @Override
    public Optional<RolDTO> buscarPorId(Long id) {
        return rolRepository.findById(Math.toIntExact(id))
                .map(rol -> modelMapper.map(rol, RolDTO.class));
    }


    @Override
    public void eliminar(Long id) {
        if (!rolRepository.existsById(Math.toIntExact(id))) {
            throw new EntityNotFoundException("No existe un rol con el ID: " + id);
        }
        rolRepository.deleteById(Math.toIntExact(id));
    }


}
