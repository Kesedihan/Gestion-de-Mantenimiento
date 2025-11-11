package com.jorel.proyectogestionmantcorrectivo.repository;

import com.jorel.proyectogestionmantcorrectivo.entity.NombreRol;
import com.jorel.proyectogestionmantcorrectivo.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para consultar los roles disponibles en el sistema.
 *
 * @author Jorel
 * @version 1.0
 *
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * Busca un rol específico por su enumeración (ADMINISTRADOR, TECNICO, TRABAJADOR).
     *
     * @param nombre enumeración del rol buscado
     * @return Optional con el rol si fue encontrado
     */
    Optional<Rol> findByNombre(NombreRol nombre);

}
