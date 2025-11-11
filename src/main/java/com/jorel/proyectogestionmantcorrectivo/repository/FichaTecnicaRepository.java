package com.jorel.proyectogestionmantcorrectivo.repository;

import com.jorel.proyectogestionmantcorrectivo.entity.FichaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {
	Optional<FichaTecnica> findByEquipo_IdEquipo(Long idEquipo);
}
