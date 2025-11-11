package com.jorel.proyectogestionmantcorrectivo.repository;

import com.jorel.proyectogestionmantcorrectivo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para acceder a los datos de los usuarios registrados.
 *
 * @author Jorel
 * @version 1.0
 *
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario según su correo electrónico.
     *
     * @param correo correo electrónico del usuario
     * @return un Optional que puede contener el usuario encontrado
     */
    Optional<Usuario> findByCorreo(String correo);


    /**
     * Verifica si existe un usuario con un correo dado.
     *
     * @param correo correo electrónico a validar
     * @return true si ya existe, false si está disponible
     */
    boolean existsByCorreo(String correo);

}
