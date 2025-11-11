package com.jorel.proyectogestionmantcorrectivo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Clase de configuracion modelMapper.
 *
 * Calse que ayuda a la reduccion de codigo gracias al manejo de mapeos manuales ayudando
 * la reduccion de errores.
 *
 * Permite transformar objetos de la capa de persistencia (entidades) en objetos que se
 * pueden exponer a través de una API REST (DTOs) y viceversa.
 *
 * @author Jorel
 * @version 1.0
 *
 */

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
